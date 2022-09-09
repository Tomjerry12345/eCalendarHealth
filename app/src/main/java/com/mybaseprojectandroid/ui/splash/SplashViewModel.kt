package com.mybaseprojectandroid.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant

class SplashViewModel(private val db: FirebaseDatabase) : ViewModel() {
    val app: LiveData<Response> = liveData {
        val response = db.getAllData(Constant.KEY_APP)
        emit(response)
    }
}