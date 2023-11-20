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

    @Multipart
    @POST("login/create_pemupukan.php")
    fun create_pemupukan(
        @Part("tanggal_pemupukan") tanggal: RequestBody,
        @Part("musim_tanam") musim_tanam: RequestBody,
        @Part("kode_wilayah") kodwil: RequestBody,
        @Part("pupuk_urea_kg") urea: RequestBody,
        @Part("pupuk_sp36_kg") sp36: RequestBody,
        @Part("pupuk_npk_kg") npk: RequestBody,
        @Part("pupuk_npk_formula") npk_form_kcl: RequestBody,
        @Part("asal_pupuk") asal_pupuk: RequestBody,
        @Part("kios") kios: RequestBody,
        @Part img: MultipartBody.Part
    ) : Call<SubmitModel>

    @Multipart
    @POST("login/create_OPT.php")
    fun create_OPT(
        @Part("luas_penyemprotan") luas_penyemprotan: RequestBody,
        @Part("kode_wilayah") kodwil: RequestBody,
        @Part("jenis_hama_penyakit") jenis_hama_penyakit: RequestBody,
        @Part("nama_obat") obat: RequestBody,
        @Part("asal_obat") asal_obat: RequestBody,
        @Part("tanggal_penyemprotan") tanggal: RequestBody,
        @Part("created_by") createdBy: RequestBody,
        @Part img: MultipartBody.Part
    ) : Call<SubmitModel>

    @Multipart
    @POST("login/create_dinamika.php")
    fun create_dinamika(
        @Part("kode_wilayah") kodwil: RequestBody,
        @Part("jenis_kejadian") jenis_kejadian: RequestBody,
        @Part("luas_cakupan") luas: RequestBody,
        @Part("potensi_pengurangan_hasil") potensi_pengurangan: RequestBody,
        @Part("tanggal_kejadian") tanggal: RequestBody,
        @Part("created_by") createdBy: RequestBody,
        @Part img: MultipartBody.Part
    ) : Call<SubmitModel>

    @Multipart
    @POST("login/create_panen.php")
    fun create_panen(
        @Part("kode_wilayah") kodwil: RequestBody,
        @Part("luas_panen") luas_panen: RequestBody,
        @Part("jumlah_panen") jumlah: RequestBody,
        @Part("tanggal_panen") tanggal: RequestBody,
        @Part("created_by") createdBy: RequestBody,
        @Part img: MultipartBody.Part
    ) : Call<SubmitModel>
}