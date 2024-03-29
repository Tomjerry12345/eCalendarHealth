package com.mybaseprojectandroid.ui.main.timer

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.DateBringWalking
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.launch

class TimerViewModel(private val db: FirebaseDatabase) : ViewModel() {

    val savedPasien = getSavedPasien()

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    private val listQuery = listOf(
        hashMapOf(
            "key" to "idUser",
            "value" to savedPasien?.id
        ),
        hashMapOf(
            "key" to "update",
            "value" to true
        )
    )

    val data: LiveData<Response> = liveData {
        val response =
            db.getDataByQuery(
                Constant.KEY_AKTIVITAS,
                listQuery as List<HashMap<String, Any>>
            )

        emit(response)
    }

    fun addAktivitas(aktivitasModel: Aktivitas) {
        showLogAssert("addAktivitas", "$aktivitasModel")
        viewModelScope.launch {
            if (aktivitasModel.id != null) {
                _response.value = db.update(Constant.KEY_AKTIVITAS, aktivitasModel.id, null, aktivitasModel)
            } else {
                _response.value = db.addData(Constant.KEY_AKTIVITAS, aktivitasModel)
            }

        }
    }

    fun addDateBringWalking(dateBringWalking: DateBringWalking) {
        viewModelScope.launch {
            _response.value = db.addData(Constant.KEY_DATE_BRING_WALKING, dateBringWalking)
        }
    }
}