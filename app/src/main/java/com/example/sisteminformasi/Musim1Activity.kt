package com.example.sisteminformasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sisteminformasi.databinding.ActivityMusim1Binding

class Musim1Activity : AppCompatActivity() {
    private var binding : ActivityMusim1Binding? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusim1Binding.inflate(layoutInflater)
        setContentView(binding!!.root)


        binding!!.btnTambahTanam.setOnClickListener {
            startActivity(Intent(this@Musim1Activity, TambahTanamActivity::class.java))
        }
    }
}