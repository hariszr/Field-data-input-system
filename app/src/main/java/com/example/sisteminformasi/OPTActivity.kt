package com.example.sisteminformasi

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.sisteminformasi.databinding.ActivityOptactivityBinding
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
import java.text.SimpleDateFormat
import java.util.*

class OPTActivity : AppCompatActivity() {
    private lateinit var binding : ActivityOptactivityBinding
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var builder : AlertDialog.Builder

    private var selectedImg : Uri? = null
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        builder = AlertDialog.Builder(this)

        displayDropDownAsalObat()
        displayDate()

        setupListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {

        binding.backProductListIV.setOnClickListener {
            finish()
        }

        val myCacheDir = File(externalCacheDir, "ImagePicker")
        myCacheDir.deleteRecursively()
        binding.addPhotoIV.setOnClickListener {
            ImagePicker.with(this)
                .saveDir(File(externalCacheDir, "ImagePicker"))
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding.kirimBTN.setOnClickListener {
            if (binding.jenisHamaPenyakitET.text.toString().isNotEmpty()) {
                if (binding.jenisHamaPenyakitET.text.toString().isNotEmpty()) {
                    if (binding.namaObatET.text.toString().isNotEmpty()) {
                        if (binding.asalObatDropDown.text.toString().isNotEmpty()) {
                            if (binding.tanggalPenyemprotanET.text.toString().isNotEmpty()) {
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

    @SuppressLint("Recycle")
    private fun createApi() {
        val myObject = MyClassForTahun()
        myObject.setTahunSaatIni()
        val tahunSaatIni = myObject.getTahun().toString()
        Log.d("TahunNow", "Tahun Saat Ini: $tahunSaatIni")

        val musimCode = intent.getIntExtra("MUSIM_CODE", 0).toString()
        Log.d("MusimCode", "Nilai musimCode: $musimCode")

        val luasPenyemprotan = binding.luasPenyemprotanET.text.toString()
//        val musimTanam = "1"
        val jenisHamaPenyakit = binding.jenisHamaPenyakitET.text.toString()
        val namaObat = binding.namaObatET.text.toString()
        val asalObat = binding.asalObatDropDown.text.toString()
        val tanggalPenyemprotan = binding.tanggalPenyemprotanET.text.toString()

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val kodwil = sharedPreferences.getString("gabKode", (-1).toString())
        val createdby = sharedPreferences.getString("id", (-1).toString())

        println("kodwil: $kodwil")
        println("createdby: $createdby")

        // Pastikan bahwa imagePath tidak kosong sebelum membuat file
        if (selectedImg != null) {

            Log.e("selected IMG", "Selected Img: ${selectedImg!!.path}")
            val file = File(selectedImg!!.path.toString())

            // Buat objek RequestBody dari file gambar
            val requestFile = RequestBody.create("image/*".toMediaType(), file)
            Log.e("file Name", "file name: ${file.name}")
            // Buat objek MultipartBody.Part dari file gambar
            val images = MultipartBody.Part.createFormData("images", file.name, requestFile)

            api.create_OPT(
                RequestBody.create("text/plain".toMediaTypeOrNull(), tahunSaatIni),
                RequestBody.create("text/plain".toMediaTypeOrNull(), musimCode),
                RequestBody.create("text/plain".toMediaTypeOrNull(), luasPenyemprotan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), kodwil.toString()),
                RequestBody.create("text/plain".toMediaTypeOrNull(), jenisHamaPenyakit),
                RequestBody.create("text/plain".toMediaTypeOrNull(), namaObat),
                RequestBody.create("text/plain".toMediaTypeOrNull(), asalObat),
                RequestBody.create("text/plain".toMediaTypeOrNull(), tanggalPenyemprotan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), createdby.toString()),
                images

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
        } else {
            Toast.makeText(this, "Gagal mendapatkan path gambar", Toast.LENGTH_SHORT).show()
        }
    }

    private fun displayDate() {
        val myCalendar = Calendar.getInstance()
        val datePicker = DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            myCalendar.set(Calendar.YEAR, year)
            myCalendar.set(Calendar.MONTH, month)
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

            updateLabel(myCalendar)
        }

        binding.tanggalPenyemprotanET.setOnClickListener {
            val dPicker = DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
            dPicker.datePicker.minDate = System.currentTimeMillis()
            dPicker.show()
        }
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
        binding.tanggalPenyemprotanET.setText(simpleDateFormat.format(myCalendar.time))
    }

    private fun displayDropDownAsalObat() {
        val itemsAsalObat = listOf("Mandiri", "Bantuan Pemerintah")
        val adapterAsalObat = ArrayAdapter(this, R.layout.item_list_dropdown, itemsAsalObat)
        binding.asalObatDropDown.setAdapter(adapterAsalObat)
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                Log.d("ImagePicker", "Image selected: $selectedImg")
                binding.addPhotoIV.setImageURI(selectedImg)
            }
        }
    }
}