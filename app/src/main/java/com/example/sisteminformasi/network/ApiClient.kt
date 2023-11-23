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
        @Part("tahun") tahun: RequestBody,
        @Part("musim_tanam") musim: RequestBody,
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
        @Part("pemupukan_ke") pemupukan_ke: RequestBody,
        @Part("tahun") tahun: RequestBody,
        @Part("musim_tanam") musim: RequestBody,
        @Part("tanggal_pemupukan") tanggal: RequestBody,
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

    @Multipart
    @POST("login/create_kelompok_tani.php")
    fun create_kelompok_tani(
        @Part("kode_wilayah") kodwil: RequestBody,
        @Part("kel_tani") kel_tani: RequestBody,
        @Part("nama_gapoktan") nama_gapoktan: RequestBody,
        @Part("provinsi") prov: RequestBody,
        @Part("kabupaten_kota") kab_kota: RequestBody,
        @Part("kecamatan") kec: RequestBody,
        @Part("desa_kelurahan") desa_kelurahan: RequestBody,
        @Part("luas_lahan") luas_lahan: RequestBody,
        @Part("nama_ketua") nama_ketua: RequestBody,
        @Part("no_ketua_poktan") no_ketua_poktan: RequestBody,
        @Part("no_ketua_gapoktan") no_ketua_gapoktan: RequestBody,
        @Part("no_penyuluh") no_penyuluh: RequestBody,
        @Part("created_by") created_by: RequestBody
    ) : Call<SubmitModel>
}