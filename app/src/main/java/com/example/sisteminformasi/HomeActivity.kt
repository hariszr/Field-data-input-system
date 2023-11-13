package com.example.sisteminformasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sisteminformasi.databinding.ActivityHomeBinding
import com.example.sisteminformasi.databinding.ActivityMusim1Binding

class HomeActivity : AppCompatActivity() {
    private var binding : ActivityHomeBinding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.btnMusimTanamI.setOnClickListener {
            startActivity(Intent(this@HomeActivity, Musim1Activity::class.java))
        }

        binding!!.btnDataKelompokTani.setOnClickListener {
            startActivity(Intent(this@HomeActivity, DataKelompokTaniActivity::class.java))
        }

    }
}