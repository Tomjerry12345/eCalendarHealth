package com.mybaseprojectandroid.ui.user.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant

class HomeViewModel(val db: FirebaseDatabase) : ViewModel() {

    val response = MutableLiveData<Response>()

    private val savedPasien = getSavedPasien()

    private val mapIdUser = hashMapOf(
        "key" to "idUser",
        "value" to savedPasien.id
    )

    private val mapHbA1C = hashMapOf(
        "key" to "jenis",
        "value" to "HbA1C"
    )

    val mapLBS = hashMapOf(
        "key" to "jenis",
        "value" to "LBS"
    )

    private val listQueryHbA1C = listOf(
        mapIdUser,
        mapHbA1C
    )

    private val listQueryLBS = listOf(
        mapIdUser,
        mapLBS
    )

    val dataHbA1C: LiveData<Response> = liveData {
        val response =
            db.getDataByQuery(
                Constant.KEY_PEMERIKSAAN,
                listQueryHbA1C as List<HashMap<String, Any>>
            )
        emit(response)
    }

    val dataLBS: LiveData<Response> = liveData {
        val response =
            db.getDataByQuery(
                Constant.KEY_PEMERIKSAAN,
                listQueryLBS as List<HashMap<String, Any>>
            )
        emit(response)
    }

    fun setData() {
        response.value = Response.Changed(Constant.listCardItem)
    }
}