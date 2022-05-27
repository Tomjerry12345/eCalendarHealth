package com.mybaseprojectandroid.repository

import com.mybaseprojectandroid.database.retrofit.RetrofitService
import com.mybaseprojectandroid.database.retrofit.myApi
import okhttp3.MultipartBody

class ExamplesApiRepository {
    private var services: RetrofitService = myApi

    suspend fun checkConnection() = services.checkConnection()
    suspend fun getData() = services.getData()
    suspend fun addData(nama: String, alamat: String, password: String, listFile: Any?) =
        services.addData(nama, alamat, password, listFile as List<MultipartBody.Part>
        )
}
