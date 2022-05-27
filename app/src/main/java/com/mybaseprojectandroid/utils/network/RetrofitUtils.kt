package com.mybaseprojectandroid.utils.network

import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream

object RetrofitUtils {

    fun createRequestFile(
        key: String,
        file: InputStream,
        mime: String
    ): MultipartBody.Part {
        val requestBodyImage: RequestBody =
            RequestBody.create(MediaType.parse(mime), file.readBytes())
        return MultipartBody.Part.createFormData(key, "test", requestBodyImage)
    }

}