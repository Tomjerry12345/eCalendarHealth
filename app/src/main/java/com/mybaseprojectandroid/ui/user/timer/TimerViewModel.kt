package com.mybaseprojectandroid.ui.user.timer

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import kotlinx.coroutines.launch

class TimerViewModel(private val db: FirebaseDatabase) : ViewModel() {

    val savedPasien = SavedData.getObject(Constant.KEY_PASIEN, PasienModel()) as PasienModel

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    val data: LiveData<Response> = liveData {
        val response = savedPasien.id?.let {
            db.getDataBy2Query(
                Constant.KEY_AKTIVITAS,
                "idUser",
                it,
                "statusUpdate",
                true
            )
        }
        response?.let { emit(it) }
    }

    fun addAktivitas(aktivitasModel: Aktivitas) {
        viewModelScope.launch {
            if (aktivitasModel.id != null) {
                _response.value = db.update(Constant.KEY_AKTIVITAS, aktivitasModel.id, null, aktivitasModel)
            } else {
                _response.value = db.addData(Constant.KEY_AKTIVITAS, aktivitasModel)
            }

        }
    }
}