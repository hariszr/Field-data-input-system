package com.example.sisteminformasi

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.ContentResolver
import android.content.Context
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.OpenableColumns
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.sisteminformasi.databinding.ActivityDataKelompokTaniBinding
import com.example.sisteminformasi.databinding.ActivityHomeBinding
import com.example.sisteminformasi.helper.DatabaseHelper
import com.example.sisteminformasi.model.Users
import com.example.sisteminformasi.network.ApiRetrofit
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class DataKelompokTaniActivity : AppCompatActivity() {

    private var binding : ActivityDataKelompokTaniBinding? = null
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var builder : AlertDialog.Builder

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataKelompokTaniBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        builder = AlertDialog.Builder(this)

        setEditText()
        setupListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {

        binding!!.backProductListIV.setOnClickListener {
            finish()
        }

        binding!!.kirimBTN.setOnClickListener {
            if (binding!!.poktanET.text.toString().isNotEmpty()) {
                if (binding!!.gapokET.text.toString().isNotEmpty()) {
                    if (binding!!.provET.text.toString().isNotEmpty()) {
                        if (binding!!.kabET.text.toString().isNotEmpty()) {
                            if (binding!!.kecET.text.toString().isNotEmpty()) {
                                if (binding!!.desaET.text.toString().isNotEmpty()) {
                                    if (binding!!.luasBahanBakuET.text.toString().isNotEmpty()) {
                                        if (binding!!.namaKetuaPOKTANET.text.toString().isNotEmpty()) {
                                            if (binding!!.hpKetuaPOKTANET.text.toString().isNotEmpty()) {
                                                if (binding!!.hpKetuaGAPOKTANET.text.toString().isNotEmpty()){
                                                    if (binding!!.hpPetugasPenyuluhET.text.toString().isNotEmpty()){
                                                        builder.setTitle("Kirim Data")
                                                            .setMessage("Apakah data yang dimasukkan sudah benar?")
                                                            .setCancelable(true)
                                                            .setNegativeButton("Batalkan") {dialogInterface, it ->
                                                                dialogInterface.cancel()
                                                            }
                                                            .setPositiveButton("Ya") {dialogInterface, it ->
                                                                createApi()
                                                            }
                                                            .show()
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    @SuppressLint("Recycle")
    private fun createApi() {
        val namaKelompokTani = binding!!.poktanET.text.toString()
        val namaGapoktan = binding!!.gapokET.text.toString()
        val provinsi = binding!!.provET.text.toString()
        val kabOrKota = binding!!.kabET.text.toString()
        val kecamatan = binding!!.kecET.text.toString()
        val desaOrKelurahan = binding!!.desaET.text.toString()
        val luasLahan = binding!!.luasBahanBakuET.text.toString()
        val namaKetuaPoktan = binding!!.namaKetuaPOKTANET.text.toString()
        val noHpKetuaPoktan = binding!!.hpKetuaPOKTANET.text.toString()
        val noHpKetuaGapoktan = binding!!.hpKetuaGAPOKTANET.text.toString()
        val noHpPenyuluh = binding!!.hpPetugasPenyuluhET.text.toString()

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val kodwil = sharedPreferences.getString("gabKode", (-1).toString())
        val createdby = sharedPreferences.getString("id", (-1).toString())

        api.create_kelompok_tani(
                RequestBody.create("text/plain".toMediaTypeOrNull(), kodwil.toString()),
                RequestBody.create("text/plain".toMediaTypeOrNull(), namaKelompokTani),
                RequestBody.create("text/plain".toMediaTypeOrNull(), namaGapoktan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), provinsi),
                RequestBody.create("text/plain".toMediaTypeOrNull(), kabOrKota),
                RequestBody.create("text/plain".toMediaTypeOrNull(), kecamatan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), desaOrKelurahan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), luasLahan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), namaKetuaPoktan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), noHpKetuaPoktan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), noHpKetuaGapoktan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), noHpPenyuluh),
                RequestBody.create("text/plain".toMediaTypeOrNull(), createdby.toString())

            ).enqueue(object : Callback<SubmitModel> {
                override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
                    if (response.isSuccessful) {
                        val result = response.body()
                        Toast.makeText(applicationContext, result!!.message, Toast.LENGTH_SHORT).show()
                        finish()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    // Tanggapan gagal atau error
                    Log.e("API Call", "Failed to make API call", t)
                    Toast.makeText(applicationContext, "Failed to make API call", Toast.LENGTH_SHORT).show()
                }
            })
    }

    private fun setEditText() {

        // Terima ID dari Intent
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)

        val provinsi = sharedPreferences.getString("provinsi", (-1).toString())
        val kabkota = sharedPreferences.getString("kab/kota", (-1).toString())
        val kecamatan = sharedPreferences.getString("kecamatan", (-1).toString())
        val desa = sharedPreferences.getString("desa", (-1).toString())
        val poktan = sharedPreferences.getString("poktan", (-1).toString())

        // Tampilkan ID di EditText
        binding!!.provET.setText(provinsi.toString())
        binding!!.kabET.setText(kabkota.toString())
        binding!!.kecET.setText(kecamatan.toString())
        binding!!.desaET.setText(desa.toString())
        binding!!.poktanET.setText(poktan.toString())

    }
}