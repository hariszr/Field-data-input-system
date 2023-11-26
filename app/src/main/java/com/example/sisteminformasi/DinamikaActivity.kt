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
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.annotation.RequiresApi
import com.example.sisteminformasi.databinding.ActivityDinamikaBinding
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

class DinamikaActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDinamikaBinding
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var builder : AlertDialog.Builder

    private var selectedImg : Uri? = null

    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Exit 0")
            if (binding.jenisKejadianDropDown.text.toString().isEmpty() && binding.luasCakupanET.text.toString().isEmpty() && binding.potensiPenguranganDropDown.text.toString().isEmpty()
                && binding.tanggalKejadianET.text.toString().isEmpty() && selectedImg == null) {
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
        binding = ActivityDinamikaBinding.inflate(layoutInflater)
        setContentView(binding.root)
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)

        builder = AlertDialog.Builder(this)

        displayDate()
        displayDropDownJenisKejadian()
        displayDropDownPotensiPenguranganHasil()

        setupListener()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {

        binding.backProductListIV.setOnClickListener {
            if (binding.jenisKejadianDropDown.text.toString().isEmpty() && binding.luasCakupanET.text.toString().isEmpty() && binding.potensiPenguranganDropDown.text.toString().isEmpty()
                && binding.tanggalKejadianET.text.toString().isEmpty() && selectedImg == null) {
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
            validation()
        }

        binding.jenisKejadianDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak diperlukan
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak diperlukan
            }

            override fun afterTextChanged(s: Editable?) {
                validationJenisKejadian()
            }
        })

        binding.potensiPenguranganDropDown.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak diperlukan
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak diperlukan
            }

            override fun afterTextChanged(s: Editable?) {
                validationPotensi()
            }
        })

        binding.tanggalKejadianET.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                // Tidak diperlukan
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                // Tidak diperlukan
            }

            override fun afterTextChanged(s: Editable?) {
                validationTanggalKejadian()
            }
        })
    }

    private fun validationJenisKejadian() {
        if (binding.jenisKejadianDropDown.text.isEmpty()) {
            binding.jenisKejadianLayout.error = "Kolom Harus Diisi"
            binding.jenisKejadianDropDown.requestFocus()
            return
        } else {
            binding.jenisKejadianLayout.error = null
            binding.jenisKejadianDropDown.clearFocus()
        }
    }

    private fun validationPotensi() {
        if (binding.potensiPenguranganDropDown.text.isEmpty()) {
            binding.potensiPenguranganLayout.error = "Kolom Harus Diisi"
            binding.potensiPenguranganDropDown.requestFocus()
            return
        } else {
            binding.potensiPenguranganLayout.error = null
            binding.potensiPenguranganDropDown.clearFocus()
        }
    }

    private fun validationTanggalKejadian() {
        if (binding.tanggalKejadianET.text.toString().isEmpty()) {
            binding.tanggalKejadianLayout.error = "Kolom Harus Diisi"
            binding.tanggalKejadianET.requestFocus()
            return
        } else {
            binding.tanggalKejadianLayout.error = null
        }
    }

    private fun validation() {
        if (binding.jenisKejadianDropDown.text.toString().isEmpty()) {
            binding.jenisKejadianLayout.error = "Kolom Harus Diisi"
            binding.jenisKejadianDropDown.requestFocus()
            return
        }
        if (binding.luasCakupanET.text.toString().isEmpty()) {
            binding.luasCakupanET.error = "Kolom Harus Diisi"
            binding.luasCakupanET.requestFocus()
            return
        }
        if (binding.potensiPenguranganDropDown.text.toString().isEmpty()) {
            binding.potensiPenguranganLayout.error = "Kolom Harus Diisi"
            binding.potensiPenguranganDropDown.requestFocus()
            return
        }
        if (binding.tanggalKejadianET.text.toString().isEmpty()) {
            binding.tanggalKejadianLayout.error = "Kolom Harus Diisi"
            binding.tanggalKejadianET.requestFocus()
            return
        } else {
            binding.tanggalKejadianLayout.error = null
            binding.tanggalKejadianET.clearFocus()
        }
        if (selectedImg == null) {
//            Toast.makeText(this, "Product image cannot be empty", Toast.LENGTH_SHORT).show()
            binding.errorPictTV.visibility = View.VISIBLE
            binding.errorPictTV.requestFocus()
            return
        } else {
            binding.errorPictTV.visibility = View.GONE
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

        val myObject = MyClassForTahun()
        myObject.setTahunSaatIni()
        val tahunSaatIni = myObject.getTahun().toString()
        Log.d("TahunNow", "Tahun Saat Ini: $tahunSaatIni")

        val musimCode = intent.getIntExtra("MUSIM_CODE", 0).toString()
        Log.d("MusimCode", "Nilai musimCode: $musimCode")

        val jenisKejadian = binding.jenisKejadianDropDown.text.toString()
//        val musimTanam = "1"
        val luasCakupan = binding.luasCakupanET.text.toString()
        val potensiPengurangan = binding.potensiPenguranganDropDown.text.toString()
        val tanggalKejadian = binding.tanggalKejadianET.text.toString()

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

            api.create_dinamika(
                RequestBody.create("text/plain".toMediaTypeOrNull(), tahunSaatIni),
                RequestBody.create("text/plain".toMediaTypeOrNull(), musimCode),
                RequestBody.create("text/plain".toMediaTypeOrNull(), kodwil.toString()),
                RequestBody.create("text/plain".toMediaTypeOrNull(), jenisKejadian),
                RequestBody.create("text/plain".toMediaTypeOrNull(), luasCakupan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), potensiPengurangan),
                RequestBody.create("text/plain".toMediaTypeOrNull(), tanggalKejadian),
                RequestBody.create("text/plain".toMediaTypeOrNull(), createdby.toString()),
                images

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
        } else {
            Toast.makeText(this, "Gagal mendapatkan path gambar", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
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

        binding.tanggalKejadianET.setOnClickListener {
            val dPicker = DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
            dPicker.datePicker.minDate = System.currentTimeMillis()
            dPicker.show()
        }
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
        binding.tanggalKejadianET.setText(simpleDateFormat.format(myCalendar.time))
    }

    private fun displayDropDownJenisKejadian() {
        val itemsJenisKejadian = listOf("Banjir", "Gempa/Badai", "Serangan OPT", "Kekeringan", "Kelangkaan Pupuk", "Kelangkaan Obat-Obatan")
        val adapterJenisKejadian = ArrayAdapter(this, R.layout.item_list_dropdown, itemsJenisKejadian)
        binding.jenisKejadianDropDown.setAdapter(adapterJenisKejadian)
    }

    private fun displayDropDownPotensiPenguranganHasil() {
        val itemsPotensi = listOf("0%-10%", "10%-20%", "20%-30%", "30%-40%", "40%-50%", "50%-60%", "60%-70%", "70%-80%", "80%-90%", "90%-100%")
        val adapterPotensi = ArrayAdapter(this, R.layout.item_list_dropdown, itemsPotensi)
        binding.potensiPenguranganDropDown.setAdapter(adapterPotensi)
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