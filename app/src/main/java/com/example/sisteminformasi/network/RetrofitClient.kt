package com.example.sisteminformasi.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class RetrofitClient {

    private fun getRetrofitClient(): Retrofit{
        return Retrofit.Builder()
            .baseUrl("http://192.168.1.21:80/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    fun getInstance(): ApiClient {
        return getRetrofitClient().create(ApiClient::class.java)
    }
}