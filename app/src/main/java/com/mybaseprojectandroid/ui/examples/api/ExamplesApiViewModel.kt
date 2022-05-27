package com.mybaseprojectandroid.ui.examples.api

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.repository.ExamplesApiRepository
import com.mybaseprojectandroid.ui.examples.ExamplesActivity
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

class ExamplesApiViewModel(private val examplesApiRepository: ExamplesApiRepository) : ViewModel() {

    fun getData(): MutableLiveData<Response> {
        val data = MutableLiveData<Response>()
        viewModelScope.launch {
            withContext(Dispatchers.IO) {
                try {
                    val resultConnection = examplesApiRepository.checkConnection()
                    if (resultConnection.code == 200) {
                        val resultData = examplesApiRepository.getData()
                        data.postValue(Response.Changed(resultData))
                    }
                } catch (throwable: Throwable) {
                    when (throwable) {
                        is IOException -> {
                            data.postValue(Response.Error("Network Error => ${throwable.message}"))
                        }
                        is HttpException -> {
                            val code = throwable.code()
                            val errorResponse = throwable.message()
                            data.postValue(Response.Error("Error $code $errorResponse"))
                        }
                        else -> {
                            data.postValue(Response.Error("Unknown Error => ${throwable.message}"))
                        }
                    }
                }
            }
        }

        return data
    }

    fun onMoveToAddData(view: View) {
        moveNavigationTo(view, R.id.action_examplesApiFragment_to_examplesApiAddFragment)
    }

    class Factory(private val examplesApiRepository: ExamplesApiRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamplesApiViewModel(examplesApiRepository) as T
        }
    }

}