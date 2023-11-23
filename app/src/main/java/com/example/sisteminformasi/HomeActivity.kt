package com.example.sisteminformasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sisteminformasi.databinding.ActivityHomeBinding
import com.example.sisteminformasi.databinding.ActivityMusim1Binding
import com.example.sisteminformasi.helper.DatabaseHelper

class HomeActivity : AppCompatActivity() {
    private var binding : ActivityHomeBinding? = null
    private var musimCode: Int = 0

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