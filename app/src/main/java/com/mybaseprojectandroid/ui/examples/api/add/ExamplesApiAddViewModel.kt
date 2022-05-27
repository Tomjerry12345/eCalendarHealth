package com.mybaseprojectandroid.ui.examples.api.add

import android.annotation.SuppressLint
import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.repository.ExamplesApiRepository
import com.mybaseprojectandroid.utils.network.RetrofitUtils
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.InputStream


@SuppressLint("StaticFieldLeak")
class ExamplesApiAddViewModel(
    val examplesApiRepository: ExamplesApiRepository
) : ViewModel() {

    lateinit var imageFile: InputStream
    lateinit var mimeImage: String
    lateinit var mimeFile: String
    lateinit var documentFile: InputStream

    fun onAddData(view: View) {
        if (::imageFile.isInitialized && ::documentFile.isInitialized) {
            uploadFile(imageFile, documentFile)
        } else {
            showLogAssert("error", "file kosong")
        }
    }

    private fun uploadFile(imageFile: InputStream, documentFile: InputStream) {
        val bodyImage = RetrofitUtils.createRequestFile("image", imageFile, mimeImage)
        val bodyFile = RetrofitUtils.createRequestFile("file", documentFile, mimeFile)

        val list = listOf(
            bodyImage,
            bodyFile
        )

        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val response = examplesApiRepository.addData("test", "alamat", "55555", list)
                    showLogAssert("response", response.toString())
                } catch (t: Throwable) {
                    showLogAssert("error", t.message.toString())
                }
            }
        }
    }

    class Factory(
        private val examplesApiRepository: ExamplesApiRepository
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamplesApiAddViewModel(examplesApiRepository) as T
        }
    }
}