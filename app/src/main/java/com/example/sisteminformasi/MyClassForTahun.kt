package com.example.sisteminformasi

import java.util.*

class MyClassForTahun {

    // Deklarasikan variabel tahun di kelas Anda
    private var tahun: Int = 0

    // Fungsi untuk mengatur nilai tahun ke tahun saat ini
    fun setTahunSaatIni() {
        val calendar = Calendar.getInstance()
        tahun = calendar.get(Calendar.YEAR)
    }

    // Fungsi untuk mendapatkan nilai tahun
    fun getTahun(): Int {
        return tahun
    }
}