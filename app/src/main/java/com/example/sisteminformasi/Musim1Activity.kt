package com.example.sisteminformasi

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.sisteminformasi.databinding.ActivityMusim1Binding

class Musim1Activity : AppCompatActivity() {
    private var binding : ActivityMusim1Binding? = null
    private var pemupukanKe : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMusim1Binding.inflate(layoutInflater)
        setContentView(binding!!.root)

        binding!!.backProductListIV.setOnClickListener {
            finish()
        }

        val musimCode = intent.getIntExtra("MUSIM_CODE", 0)
        Log.d("MusimCode", "Nilai MusimCode Di Halaman Musim Activity: $musimCode")

        binding!!.numberOfMusimTV.text = musimCode.toString()

        binding!!.btnTambahTanam.setOnClickListener {
            val intent = Intent(this, TambahTanamActivity::class.java)
            intent.putExtra("MUSIM_CODE", musimCode)
            startActivity(intent)
        }

        binding!!.btnPemupukan1.setOnClickListener {

            val intent = Intent(this, PemupukanActivity::class.java)
            intent.putExtra("MUSIM_CODE", musimCode)
            intent.putExtra("PEMUPUKAN_KE", 1)
            startActivity(intent)
        }

        binding!!.btnPemupukan2.setOnClickListener {

            val intent = Intent(this, PemupukanActivity::class.java)
            intent.putExtra("MUSIM_CODE", musimCode)
            intent.putExtra("PEMUPUKAN_KE", 2)
            startActivity(intent)
        }

        binding!!.btnPengendalianOPT.setOnClickListener {

            val intent = Intent(this, OPTActivity::class.java)
            intent.putExtra("MUSIM_CODE", musimCode)
            startActivity(intent)
        }

        binding!!.btnDinamikaBudidaya.setOnClickListener {

            val intent = Intent(this, DinamikaActivity::class.java)
            intent.putExtra("MUSIM_CODE", musimCode)
            startActivity(intent)
        }

        binding!!.btnPanen.setOnClickListener {

            val intent = Intent(this, PanenActivity::class.java)
            intent.putExtra("MUSIM_CODE", musimCode)
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()

        // Reset nilai pemupukanKe ke 0 saat HomeActivity kembali aktif
        pemupukanKe = 0

        // Menampilkan nilai musimCode ke dalam log
        Log.d("PemupukanKe", "Nilai PemupukanKe setelah kembali ke MusimActivity: $pemupukanKe")
    }
}