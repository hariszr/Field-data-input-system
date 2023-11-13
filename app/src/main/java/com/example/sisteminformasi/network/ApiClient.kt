package com.example.sisteminformasi.network

import com.example.sisteminformasi.SubmitModel
import com.example.sisteminformasi.model.ResponseLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiClient {

    @FormUrlEncoded
    @POST("login/login_service.php")
    fun login(
        @Field("post_username") username : String,
        @Field("post_password") password : String
    ): retrofit2.Call<ResponseLogin>

    @Multipart
    @POST("login/create.php")
    fun create(
        @Part("luas_tambah_tanam") luas_tambah_tanam: RequestBody,
        @Part("varietas_benih") varietas: RequestBody,
        @Part("jumlah_benih") jumlah: RequestBody,
        @Part("asal_benih") asal: RequestBody,
        @Part("tanggal_tanam") tanggal_tanam: RequestBody,
        @Part dokumentasi: MultipartBody.Part
    ) : Call<SubmitModel>
}