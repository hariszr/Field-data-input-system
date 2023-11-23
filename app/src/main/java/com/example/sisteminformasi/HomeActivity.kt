package com.example.sisteminformasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sisteminformasi.databinding.ActivityHomeBinding
import com.example.sisteminformasi.databinding.ActivityMusim1Binding
import com.example.sisteminformasi.helper.DatabaseHelper

class HomeActivity : AppCompatActivity() {
    private var binding : ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

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
//            startActivity(Intent(this@HomeActivity, DataKelompokTaniActivity::class.java))

            val intent = Intent(this@HomeActivity, DataKelompokTaniActivity::class.java)
            startActivity(intent)
            // Mendapatkan objek Users dari database SQLite
//            val dbHelper = DatabaseHelper(this)
//            val user = dbHelper.getUser("nama_pengguna", "N") // Ganti dengan kondisi atau metode yang sesuai
//
//            // Memulai aktivitas dan mengirimkan objek Users melalui Intent
//            val intent = Intent(this, DataKelompokTaniActivity::class.java).apply {
//                putExtra("USER_DATA", user)
//            }
//            startActivity(intent)
        }
    }
}