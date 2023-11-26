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
import androidx.activity.OnBackPressedCallback
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

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Exit 0")
            if (binding!!.poktanET.text.toString().isEmpty() && binding!!.gapokET.text.toString().isEmpty() && binding!!.provET.text.toString().isEmpty()
                && binding!!.kecET.text.toString().isEmpty() && binding!!.kabET.text.toString().isEmpty() && binding!!.desaET.text.toString().isEmpty()
                && binding!!.luasBahanBakuET.text.toString().isEmpty() && binding!!.namaKetuaPOKTANET.text.toString().isEmpty() && binding!!.hpKetuaPOKTANET.text.toString().isEmpty()
                && binding!!.hpKetuaGAPOKTANET.text.toString().isEmpty() && binding!!.hpPetugasPenyuluhET.text.toString().isEmpty()) {
                finish()
            } else {
                builder.setTitle("Batalkan Pengisian Data")
                    .setMessage("Apakah anda yakin ingin membatalkan?")
                    .setCancelable(true)
                    .setNegativeButton("Tidak") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .setPositiveButton("Ya") { dialogInterface, it ->
                        finish()
                    }
                    .show()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataKelompokTaniBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)

        builder = AlertDialog.Builder(this)

        setEditText()
        setupListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {

        binding!!.backProductListIV.setOnClickListener {
            if (binding!!.poktanET.text.toString().isEmpty() && binding!!.gapokET.text.toString().isEmpty() && binding!!.provET.text.toString().isEmpty()
                && binding!!.kecET.text.toString().isEmpty() && binding!!.kabET.text.toString().isEmpty() && binding!!.desaET.text.toString().isEmpty()
                && binding!!.luasBahanBakuET.text.toString().isEmpty() && binding!!.namaKetuaPOKTANET.text.toString().isEmpty() && binding!!.hpKetuaPOKTANET.text.toString().isEmpty()
                && binding!!.hpKetuaGAPOKTANET.text.toString().isEmpty() && binding!!.hpPetugasPenyuluhET.text.toString().isEmpty()) {
                finish()
            } else {
                builder.setTitle("Batalkan Pengisian Data")
                    .setMessage("Apakah anda yakin ingin membatalkan?")
                    .setCancelable(true)
                    .setNegativeButton("Tidak") { dialogInterface, it ->
                        dialogInterface.cancel()
                    }
                    .setPositiveButton("Ya") { dialogInterface, it ->
                        finish()
                    }
                    .show()
            }
        }

        binding!!.kirimBTN.setOnClickListener {
            validation()
        }
    }

    private fun validation() {
        if (binding!!.poktanET.text.toString().isEmpty()) {
            binding!!.poktanET.error = "Kolom Harus Diisi"
            binding!!.poktanET.requestFocus()
            return
        }
        if (binding!!.gapokET.text.toString().isEmpty()) {
            binding!!.gapokET.error = "Kolom Harus Diisi"
            binding!!.gapokET.requestFocus()
            return
        }
        if (binding!!.provET.text.toString().isEmpty()) {
            binding!!.provET.error = "Kolom Harus Diisi"
            binding!!.provET.requestFocus()
            return
        }
        if (binding!!.kabET.text.toString().isEmpty()) {
            binding!!.kabET.error = "Kolom Harus Diisi"
            binding!!.kabET.requestFocus()
            return
        }
        if (binding!!.kecET.text.toString().isEmpty()) {
            binding!!.kecET.error = "Kolom Harus Diisi"
            binding!!.kecET.requestFocus()
            return
        }
        if (binding!!.desaET.text.toString().isEmpty()) {
            binding!!.desaET.error = "Kolom Harus Diisi"
            binding!!.desaET.requestFocus()
            return
        }
        if (binding!!.luasBahanBakuET.text.toString().isEmpty()) {
            binding!!.luasBahanBakuET.error = "Kolom Harus Diisi"
            binding!!.luasBahanBakuET.requestFocus()
            return
        }
        if (binding!!.namaKetuaPOKTANET.text.toString().isEmpty()) {
            binding!!.namaKetuaPOKTANET.error = "Kolom Harus Diisi"
            binding!!.namaKetuaPOKTANET.requestFocus()
            return
        }
        if (binding!!.hpKetuaPOKTANET.text.toString().isEmpty()) {
            binding!!.hpKetuaPOKTANET.error = "Kolom Harus Diisi"
            binding!!.hpKetuaPOKTANET.requestFocus()
            return
        } else if (binding!!.hpKetuaPOKTANET.text.toString().length > 14) { /** ini tidak berguna sebenrnya, karena sudah dibawah 6000 dan tidak akan lebih dari 4 digit, isoke buat belajar **/
            binding!!.hpKetuaPOKTANET.error = "Nomor telpon tidak dikenali"
            binding!!.hpKetuaPOKTANET.requestFocus()
            return
        } else if (binding!!.hpKetuaPOKTANET.text.toString().length < 10) { /** ini tidak berguna sebenrnya, karena sudah dibawah 6000 dan tidak akan lebih dari 4 digit, isoke buat belajar **/
            binding!!.hpKetuaPOKTANET.error = "Nomor telpon tidak dikenali"
            binding!!.hpKetuaPOKTANET.requestFocus()
            return
        } else {
            binding!!.hpKetuaPOKTANET.error = null
        }
        if (binding!!.hpKetuaGAPOKTANET.text.toString().isEmpty()) {
            binding!!.hpKetuaGAPOKTANET.error = "Kolom Harus Diisi"
            binding!!.hpKetuaGAPOKTANET.requestFocus()
            return
        } else if (binding!!.hpKetuaGAPOKTANET.text.toString().length > 14) { /** ini tidak berguna sebenrnya, karena sudah dibawah 6000 dan tidak akan lebih dari 4 digit, isoke buat belajar **/
            binding!!.hpKetuaGAPOKTANET.error = "Nomor telpon tidak dikenali"
            binding!!.hpKetuaGAPOKTANET.requestFocus()
            return
        } else if (binding!!.hpKetuaGAPOKTANET.text.toString().length < 10) { /** ini tidak berguna sebenrnya, karena sudah dibawah 6000 dan tidak akan lebih dari 4 digit, isoke buat belajar **/
            binding!!.hpKetuaGAPOKTANET.error = "Nomor telpon tidak dikenali"
            binding!!.hpKetuaGAPOKTANET.requestFocus()
            return
        } else {
            binding!!.hpKetuaGAPOKTANET.error = null
        }
        if (binding!!.hpPetugasPenyuluhET.text.toString().isEmpty()) {
            binding!!.hpPetugasPenyuluhET.error = "Kolom Harus Diisi"
            binding!!.hpPetugasPenyuluhET.requestFocus()
            return
        } else if (binding!!.hpPetugasPenyuluhET.text.toString().length > 14) { /** ini tidak berguna sebenrnya, karena sudah dibawah 6000 dan tidak akan lebih dari 4 digit, isoke buat belajar **/
            binding!!.hpPetugasPenyuluhET.error = "Nomor telpon tidak dikenali"
            binding!!.hpPetugasPenyuluhET.requestFocus()
            return
        } else if (binding!!.hpPetugasPenyuluhET.text.toString().length < 10) { /** ini tidak berguna sebenrnya, karena sudah dibawah 6000 dan tidak akan lebih dari 4 digit, isoke buat belajar **/
            binding!!.hpPetugasPenyuluhET.error = "Nomor telpon tidak dikenali"
            binding!!.hpPetugasPenyuluhET.requestFocus()
            return
        } else {
            binding!!.hpPetugasPenyuluhET.error = null
        }
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
    @SuppressLint("Recycle")
    private fun createApi() {
        val builder = androidx.appcompat.app.AlertDialog.Builder(this)
        builder.setCancelable(false)
            .setView(R.layout.layout_progress)
        val dialog = builder.create()
        dialog.show()

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
                        dialog.dismiss()
                    }
                }

                override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
                    // Tanggapan gagal atau error
                    Log.e("API Call", "Failed to make API call", t)
                    Toast.makeText(applicationContext, "Failed to make API call", Toast.LENGTH_SHORT).show()
                    dialog.dismiss()
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