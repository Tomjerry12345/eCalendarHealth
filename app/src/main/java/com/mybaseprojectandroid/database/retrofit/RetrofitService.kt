package com.mybaseprojectandroid.database.retrofit

import com.mybaseprojectandroid.model.ExamplesApiModel
import com.mybaseprojectandroid.utils.other.Constant.BASE_URL
import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface RetrofitService {
    @GET("/mysql/checkConnection")
    suspend fun checkConnection(): ExamplesApiModel

    @GET("/mysql/get")
    suspend fun getData(): ExamplesApiModel

    @Multipart
    @POST("/mysql/add")
    suspend fun addData(
        @Part("nama") nama: String,
        @Part("alamat") alamat: String,
        @Part("password") password: String,
        @Part listFile: List<MultipartBody.Part>
    ) : ExamplesApiModel
}

val myApi: RetrofitService by lazy {
    Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(RetrofitService::class.java)
}