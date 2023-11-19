package com.example.sisteminformasi

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.sisteminformasi.databinding.ActivityDataKelompokTaniBinding
import com.example.sisteminformasi.databinding.ActivityHomeBinding
import com.example.sisteminformasi.helper.DatabaseHelper
import com.example.sisteminformasi.model.Users

class DataKelompokTaniActivity : AppCompatActivity() {

    private var binding : ActivityDataKelompokTaniBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataKelompokTaniBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        // Terima ID dari Intent
        val sharedPreferences = getSharedPreferences("user_data", Context.MODE_PRIVATE)
        val provinsi = sharedPreferences.getString("provinsi", (-1).toString())
        val kabkota = sharedPreferences.getString("kab/kota", (-1).toString())
        val kecamatan = sharedPreferences.getString("kecamatan", (-1).toString())
        val desa = sharedPreferences.getString("desa", (-1).toString())
        val poktan = sharedPreferences.getString("poktan", (-1).toString())

        // Tampilkan ID di EditText
        binding!!.provET.setText(provinsi.toString())
        binding!!.kabET.setText(kabkota.toString())
        binding!!.kecET.setText(kecamatan.toString())
        binding!!.desaET.setText(desa.toString())
        binding!!.poktanET.setText(poktan.toString())

        setEditText()
    }

    private fun setEditText() {
//
//        val users =
//        binding!!.kabET.setText(users.username)
//        binding!!.kecET.setText(users.id.toString())

    }
}