package com.example.sisteminformasi

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import com.example.sisteminformasi.databinding.ActivityHomeBinding
import com.example.sisteminformasi.databinding.ActivityMusim1Binding
import com.example.sisteminformasi.helper.DatabaseHelper

class HomeActivity : AppCompatActivity() {
    private var binding : ActivityHomeBinding? = null
    private var musimCode: Int = 0
    private lateinit var builder: AlertDialog.Builder

    private var backPressedTime = 0L
    val onBackPressedCallback = object : OnBackPressedCallback(true) {
        override fun handleOnBackPressed() {
            println("Exit 0")
            if (backPressedTime + 2000 > System.currentTimeMillis()) {
                finishAffinity()
            } else {
                Toast.makeText(this@HomeActivity, "Tekan sekali lagi untuk keluar",
                    Toast.LENGTH_SHORT).show()
            }
            backPressedTime = System.currentTimeMillis()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)
        onBackPressedDispatcher.addCallback(this,onBackPressedCallback)
        builder = AlertDialog.Builder(this)

        binding!!.signOutTV.setOnClickListener {
            builder.setTitle("Keluar Akun!")
                .setMessage("Apakah Anda Yakin Ingin Keluar dari Akun Ini?")
                .setCancelable(true)
                .setNegativeButton("Tidak") {dialogInterface, it ->
                    dialogInterface.cancel()
                }
                .setPositiveButton("Iya") {dialogInterface, it ->
                    val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
                    val editor = sharedPreferences.edit()
                    editor.putString("id", "")
                    editor.putString("kodwil", "")
                    editor.apply()

                    startActivity(Intent(this, LoginActivity::class.java))
                    Toast.makeText(this, "Berhasil Keluar dari Akun", Toast.LENGTH_SHORT).show()
                    finishAffinity()
                }
                .show()
        }

        binding!!.btnMusimTanamI.setOnClickListener {
            val intent = Intent(this, Musim1Activity::class.java)
            intent.putExtra("MUSIM_CODE", 1)
            startActivity(intent)
        }
        binding!!.btnMusimTanamII.setOnClickListener {
            val intent = Intent(this, Musim1Activity::class.java)
            intent.putExtra("MUSIM_CODE", 2)
            startActivity(intent)
        }
        binding!!.btnMusimTanamIII.setOnClickListener {
            val intent = Intent(this, Musim1Activity::class.java)
            intent.putExtra("MUSIM_CODE", 3)
            startActivity(intent)
        }

        binding!!.btnMusimTanamIV.setOnClickListener {
            val intent = Intent(this, Musim1Activity::class.java)
            intent.putExtra("MUSIM_CODE", 4)
            startActivity(intent)
        }

        binding!!.btnDataKelompokTani.setOnClickListener {

            val intent = Intent(this@HomeActivity, DataKelompokTaniActivity::class.java)
            startActivity(intent)
        }
    }
    override fun onResume() {
        super.onResume()

        // Reset nilai musimCode ke 0 saat HomeActivity kembali aktif
        musimCode = 0

        // Menampilkan nilai musimCode ke dalam log
        Log.d("MusimCode", "Nilai musimCode setelah kembali ke HomeActivity: $musimCode")
    }
}