package com.example.sisteminformasi.network

import com.example.sisteminformasi.SubmitModel
import com.example.sisteminformasi.model.ResponseLogin
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiClient {

    @FormUrlEncoded
    @POST("login/login_service.php")
    fun login(
        @Field("post_username") username : String,
        @Field("post_password") password : String
    ): retrofit2.Call<ResponseLogin>


    @Multipart
    @POST("login/create2.php")
    fun create(
        @Part("luas_tambah") luas_tambah_tanam: RequestBody,
        @Part("varietas_id") varietas: RequestBody,
        @Part("jumlah_benih") jumlah: RequestBody,
        @Part("asal_benih") asal: RequestBody,
        @Part("tanggal_input") tanggal: RequestBody,
        @Part("kode_wilayah") kodwil: RequestBody,
        @Part("created_by") createdby: RequestBody,
        @Part img: MultipartBody.Part
    ) : Call<SubmitModel>
}