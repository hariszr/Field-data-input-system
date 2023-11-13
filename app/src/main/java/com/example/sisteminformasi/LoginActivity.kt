package com.example.sisteminformasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.sisteminformasi.databinding.ActivityLoginBinding
import com.example.sisteminformasi.model.ResponseLogin
import com.example.sisteminformasi.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginActivity : AppCompatActivity() {
    private var binding : ActivityLoginBinding? = null
    private var user: String = ""
    private var pass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

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
                    getData()
                }
            }
        }
    }

    private fun getData() {
        val api = RetrofitClient().getInstance()
        api.login(user, pass).enqueue(object : Callback<ResponseLogin>{
            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                if (response.isSuccessful) {
                    if (response.body()?.response == true) {
                        binding!!.loading.visibility = View.GONE
                        startActivity(Intent(this@LoginActivity, HomeActivity::class.java))
                        finish()
                    } else {
                        binding!!.loading.visibility = View.GONE
                        Toast.makeText(this@LoginActivity, "Login Gagal! Periksa kembali username dan password", Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login Gagal! Terjadi kesalahan", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("Login Error", "${t.message}")
            }

        })
    }
}