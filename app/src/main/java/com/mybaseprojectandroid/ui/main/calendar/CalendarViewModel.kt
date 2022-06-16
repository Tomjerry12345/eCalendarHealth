package com.mybaseprojectandroid.ui.main.calendar

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant

class CalendarViewModel(val db: FirebaseDatabase) : ViewModel() {

    private val savedPasien = getSavedPasien()

    private val mapIdUser = hashMapOf(
        "key" to "idUser",
        "value" to savedPasien?.id
    )

    private val listQuery = listOf(
        mapIdUser
    )

    val data: LiveData<Response> = liveData {
        val response =
            db.getDataByQuery(
                Constant.KEY_AKTIVITAS,
                listQuery as List<HashMap<String, Any>>
            )
        emit(response)
    }
}