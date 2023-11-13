package com.example.sisteminformasi.model

data class PayloadLogin (
    val provinsi : String,
    val kabupatenkota : String,
    val kecamatan : String,
    val desakelurahan : String,
    val username : String
)