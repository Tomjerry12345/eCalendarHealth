package com.mybaseprojectandroid.ui.user.home

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant

class HomeViewModel() : ViewModel() {

    val response = MutableLiveData<Response>()

    fun setData() {
        response.value = Response.Changed(Constant.listCardItem)
    }
}