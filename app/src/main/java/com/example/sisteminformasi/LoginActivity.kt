package com.example.sisteminformasi

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.room.Room
import com.example.sisteminformasi.databinding.ActivityLoginBinding
import com.example.sisteminformasi.helper.DatabaseHelper
import com.example.sisteminformasi.model.ResponseLogin
import com.example.sisteminformasi.network.RetrofitClient
import com.example.sisteminformasi.network.UsersDao
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var binding : ActivityLoginBinding? = null
    private var user: String = ""
    private var pass: String = ""

    private lateinit var db: DatabaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        db = DatabaseHelper(this)

        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val getUsername = sharedPreferences.getString("id", "")
        val getPassword = sharedPreferences.getString("kodwil", "")

        if (getUsername != "" && getPassword != "") {
            val i = Intent(this, HomeActivity::class.java)
            startActivity(i)
        }

        binding!!.btnLogin.setOnClickListener {
            user = binding!!.usernameET.text.toString()
            pass = binding!!.passwordET.text.toString()

            when {
                user == "" -> {
                    binding!!.usernameET.error = "Username tidak boleh kososng"
                }
                pass == "" -> {
                    binding!!.passwordET.error = "Password tidak boleh kososng"
                }
                else -> {
                    binding!!.loading.visibility = View.VISIBLE
//                    getData()
                    getData2()
                }
            }
        }
    }

    private fun getData2() {

        // Ambil data dari inputan pengguna
        val enteredUsername = binding?.usernameET?.text.toString()
        val enteredPassword = binding?.passwordET?.text.toString()

        // Inisialisasi objek DatabaseHelper
        val dbHelper = DatabaseHelper(this)

        // Panggil fungsi getUser untuk mendapatkan data user berdasarkan username dan password
        val user = dbHelper.getUser(enteredUsername, enteredPassword)

        // Cek apakah user ditemukan atau tidak
        if (user != null) {

            // Simpan userId di SharedPreferences
            val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
            val editor = sharedPreferences.edit()

            editor.putString("provinsi", user.prop)
            editor.putString("kab/kota", user.kab)
            editor.putString("kecamatan", user.kec)
            editor.putString("desa", user.desa)
            editor.putString("poktan", user.nama_poktan)

            editor.putString("gabKode", user.gab_kode)
            editor.putString("id", user.id.toString())
            editor.putString("kodwil", user.kodwil)
            editor.apply()

            // Login berhasil, lakukan aksi sesuai kebutuhan
            // Misalnya, arahkan ke halaman utama atau tampilkan pesan sukses
            binding!!.loading.visibility = View.GONE
            startActivity(Intent(this@LoginActivity, HomeActivity::class.java))

            Toast.makeText(this, "Login sukses", Toast.LENGTH_SHORT).show()
            finish()
        } else {
            // Login gagal, tampilkan pesan kesalahan atau lakukan aksi sesuai kebutuhan
            // Misalnya, tampilkan Toast
            Toast.makeText(this, "Login gagal, username atau password salah", Toast.LENGTH_SHORT).show()
            binding!!.loading.visibility = View.GONE
        }

    }

    override fun onResume() {
        super.onResume()
    }

//    private fun getData() {
//        val api = RetrofitClient().getInstance()
//        api.login(user, pass).enqueue(object : Callback<ResponseLogin>{
//            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
//                if (response.isSuccessful) {
//                    if (response.body()?.response == true) {
//                        binding!!.loading.visibility = View.GONE
//                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
//                        finish()
//                    } else {
//                        binding!!.loading.visibility = View.GONE
//                        Toast.makeText(this@LoginActivity, "Login Gagal! Periksa kembali username dan password", Toast.LENGTH_LONG).show()
//                    }
//                } else {
//                    Toast.makeText(this@LoginActivity, "Login Gagal! Terjadi kesalahan", Toast.LENGTH_LONG).show()
//                }
//            }
//
//            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
//                Log.e("Login Error", "${t.message}")
//            }
//
//        })
//    }
}