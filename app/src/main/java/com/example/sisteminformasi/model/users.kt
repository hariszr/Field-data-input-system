package com.example.sisteminformasi.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Users(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
//    val kode_prop: String,
//    val kode_kab: String,
//    val kode_kec: String,
//    val kode_desa: String,
    val prop: String,
    val kab: String,
    val kec: String,
    val desa: String,
//    val status_adm: String,
    val gab_kode: String,
    val nama_poktan: String,
//    val kode_poktan: String,
    val username: String,
    val kodwil: String
)
