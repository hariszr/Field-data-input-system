package com.example.sisteminformasi

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.sisteminformasi.databinding.ActivityTambahTanamBinding
import com.example.sisteminformasi.network.ApiRetrofit
import com.github.dhaval2404.imagepicker.ImagePicker
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentResolver
import android.content.Context
import android.os.Build
import android.provider.OpenableColumns
import androidx.annotation.RequiresApi
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

private const val STORAGE_REQUEST_CODE = 101

class TambahTanamActivity : AppCompatActivity() {
    private var binding : ActivityTambahTanamBinding? = null
    private val api by lazy { ApiRetrofit().endpoint }
    private lateinit var builder : AlertDialog.Builder

    private var selectedImg : Uri? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTambahTanamBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        builder = AlertDialog.Builder(this)

        displayDropDownVarietas()
        displayDropDownAsalBenih()
        displayDate()

        setupListener()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupListener() {

        binding!!.backProductListIV.setOnClickListener {
            finish()
        }
//        val activityResultLauncher = registerForActivityResult<Intent, ActivityResult>(
//            ActivityResultContracts.StartActivityForResult()){ result ->
//            if (result.resultCode == RESULT_OK){
//                val data = result.data
//                uri = data!!.data
//                binding!!.addPhotoIV.setImageURI(uri)
//            } else {
//                Toast.makeText(this, "Dibatalkan", Toast.LENGTH_SHORT).show()
//            }
//        }
        val myCacheDir = File(externalCacheDir, "ImagePicker")
        myCacheDir.deleteRecursively()
        binding!!.addPhotoIV.setOnClickListener {
            ImagePicker.with(this)
                .saveDir(File(externalCacheDir, "ImagePicker"))
                .crop()
                .compress(1024)
                .maxResultSize(1080, 1080)
                .start()
        }

        binding!!.kirimBTN.setOnClickListener {
            if (binding!!.luasTambahTanamET.text.toString().isNotEmpty()) {
                if (binding!!.varietasDropDown.text.toString().isNotEmpty()) {
                    if (binding!!.jumlahBenihET.text.toString().isNotEmpty()) {
                        if (binding!!.asalBenihDropDown.text.toString().isNotEmpty()) {
                            if (binding!!.tanggalTanamET.text.toString().isNotEmpty()) {
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
    @SuppressLint("Range")
    fun ContentResolver.getFileName(uri: Uri): String {
        var name = ""
        val cursor = query(uri, null, null, null, null)
        cursor?.use {
            it.moveToFirst()
            name = cursor.getString(it.getColumnIndex(OpenableColumns.DISPLAY_NAME))
        }
        return name
    }

    @SuppressLint("Recycle")
    private fun createApi() {
        val myObject = MyClassForTahun()
        myObject.setTahunSaatIni()
        val tahunSaatIni = myObject.getTahun().toString()
        Log.d("TahunNow", "Tahun Saat Ini: $tahunSaatIni")

        val musimCode = intent.getIntExtra("MUSIM_CODE", 0).toString()
        Log.d("MusimCode", "Nilai musimCode: $musimCode")

        val luasTambahTanam = binding!!.luasTambahTanamET.text.toString()
        val varietas = binding!!.varietasDropDown.text.toString()
        val jumlahBenih = binding!!.jumlahBenihET.text.toString()
        val asalBenih = binding!!.asalBenihDropDown.text.toString()
        val tanggalInput = binding!!.tanggalTanamET.text.toString()

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val kodwil = sharedPreferences.getString("gabKode", (-1).toString())
        val createdby = sharedPreferences.getString("id", (-1).toString())

        println("kodwil: $kodwil")
        println("createdby: $createdby")

//        val parceFileDescriptor = contentResolver.openFileDescriptor(selectedImg!!, "r", null)?: return
//        val file = File(cacheDir, contentResolver.getFileName(selectedImg!!))
//        val inputStream = FileInputStream(parceFileDescriptor.fileDescriptor)
//        val outputStream = FileOutputStream(file)
//        inputStream.copyTo(outputStream)
//
//        val imagePath: String = getRealPathFromURI(selectedImg!!) ?: ""
//
//        println("Image Path: $imagePath")
//
//        val file1 = File(imagePath)
//
//        val requestFile = RequestBody.create("image/*".toMediaType(), file1)
//
//        api.create(
//            RequestBody.create("text/plain".toMediaTypeOrNull(), luasTambahTanam),
//            RequestBody.create("text/plain".toMediaTypeOrNull(), varietas),
//            RequestBody.create("text/plain".toMediaTypeOrNull(), jumlahBenih),
//            RequestBody.create("text/plain".toMediaTypeOrNull(), asalBenih),
//            RequestBody.create("text/plain".toMediaTypeOrNull(), tanggalTanam),
//            MultipartBody.Part.createFormData("dokumentasi", file.name, requestFile)
//        ).enqueue(object : Callback<SubmitModel> {
//            override fun onResponse(call: Call<SubmitModel>, response: Response<SubmitModel>) {
//                if (response.isSuccessful) {
//                    val result = response.body()
//                    Toast.makeText(applicationContext, result!!.message, Toast.LENGTH_SHORT).show()
//                    finish()
//                }
//            }
//
//            override fun onFailure(call: Call<SubmitModel>, t: Throwable) {
//                // Tanggapan gagal atau error
//                Toast.makeText(applicationContext, "Failed to make API call", Toast.LENGTH_SHORT).show()
//            }
//        })

        // Ambil path file gambar dari Uri yang sudah dipilih
//        val imagePath: String = getRealPathFromUri(selectedImg!!) ?: ""

//        println("nilai image Path = $imagePath")

        // Pastikan bahwa imagePath tidak kosong sebelum membuat file
        if (selectedImg != null) {

            Log.e("selected IMG", "Selected Img: ${selectedImg!!.path}")
            val file = File(selectedImg!!.path.toString())

            // Buat objek RequestBody dari file gambar
            val requestFile = RequestBody.create("image/*".toMediaType(), file)
            Log.e("file Name", "file name: ${file.name}")
            // Buat objek MultipartBody.Part dari file gambar
            val images = MultipartBody.Part.createFormData("images", file.name, requestFile)

            api.create(
                RequestBody.create("text/plain".toMediaTypeOrNull(), tahunSaatIni),
                RequestBody.create("text/plain".toMediaTypeOrNull(), musimCode),
                RequestBody.create("text/plain".toMediaTypeOrNull(), luasTambahTanam),
                RequestBody.create("text/plain".toMediaTypeOrNull(), varietas),
                RequestBody.create("text/plain".toMediaTypeOrNull(), jumlahBenih),
                RequestBody.create("text/plain".toMediaTypeOrNull(), asalBenih),
                RequestBody.create("text/plain".toMediaTypeOrNull(), tanggalInput),
                RequestBody.create("text/plain".toMediaTypeOrNull(), kodwil.toString()),
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

    private fun getRealPathFromURI(contentURI: Uri): String? {
        val cursor: Cursor? = contentResolver.query(contentURI, null, null, null, null)
        return cursor?.use {
            if (it.moveToFirst()) {
                val idx: Int = it.getColumnIndex(MediaStore.Images.ImageColumns.DATA)
                it.getString(idx)
            } else {
                // Source is Dropbox or other similar local file path
                contentURI.path
            }
        }
    }

    // Metode untuk mendapatkan path file dari Uri
    private fun getRealPathFromUri(uri: Uri): String? {

        println("nilai selectedImg = $uri")

        val filePathColumn = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = contentResolver.query(uri, filePathColumn, null, null, null)

        println("nilai cursor = $cursor")

        return if (cursor != null) {
            val columnIndex = cursor.getColumnIndex(filePathColumn[0])
            val filePath = cursor.getString(columnIndex)
            cursor.close()
            filePath
        } else {
            Log.e("ImagePicker", "Failed to get file path from URI : $uri")
            null
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

        binding!!.tanggalTanamET.setOnClickListener {
            val dPicker = DatePickerDialog(this, datePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(
                Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH))
            dPicker.datePicker.minDate = System.currentTimeMillis()
            dPicker.show()
        }
    }
    private fun updateLabel(myCalendar: Calendar) {
        val myFormat = "yyyy-MM-dd"
        val simpleDateFormat = SimpleDateFormat(myFormat, Locale.UK)
        binding!!.tanggalTanamET.setText(simpleDateFormat.format(myCalendar.time))
    }


    private fun displayDropDownVarietas() {
        val itemsVarietas = listOf("Inpari 11", "Inpari 12", "Inpari 13", "Inpari 18", "Inpari 19", "Inpari 20",
        "Inpari 33", "Inpari 42", "Inpari 43", "Inpari Sedenuk", "Dodokan", "Silugonggo", "M70D", "Padjajaran",
            "Siliwangi", "Widas", "Ciherang", "IR64", "Lainnya")
        val adapterVarietas = ArrayAdapter(this, R.layout.item_list_dropdown, itemsVarietas)
        binding!!.varietasDropDown.setAdapter(adapterVarietas)
    }

    private fun displayDropDownAsalBenih() {
        val itemsAsalBenih = listOf("Mandiri", "Bantuan Pemerintah")
        val adapterAsalBenih = ArrayAdapter(this, R.layout.item_list_dropdown, itemsAsalBenih)
        binding!!.asalBenihDropDown.setAdapter(adapterAsalBenih)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (data != null) {
            if (data.data != null) {
                selectedImg = data.data!!
                Log.d("ImagePicker", "Image selected: $selectedImg")
                binding!!.addPhotoIV.setImageURI(selectedImg)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupPermissions() {
        val permission = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)

        if (permission == PackageManager.PERMISSION_DENIED) {
            makeRequest()
        } else {
            createApi()
        }
    }

    private fun makeRequest() {
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), STORAGE_REQUEST_CODE)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == STORAGE_REQUEST_CODE) {
            if (grantResults.isEmpty() || grantResults[0] == PackageManager.PERMISSION_DENIED) {
                Toast.makeText(this@TambahTanamActivity,
                    "Storage Permission Granted",
                    Toast.LENGTH_LONG).show()
                createApi()
            } else {
                Toast.makeText(this@TambahTanamActivity,
                    "You need to storage permission to be able to use this app!",
                    Toast.LENGTH_SHORT).show()
            }
        }
    }
}