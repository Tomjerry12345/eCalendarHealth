package com.mybaseprojectandroid.ui.main.home.pasien

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import kotlinx.coroutines.launch

class HomePasienViewModel(private val db: FirebaseDatabase) : ViewModel() {

    val response = MutableLiveData<Response>()

    private val savedPasien = getSavedPasien()

    private val mapIdUser = hashMapOf(
        "key" to "idUser",
        "value" to savedPasien?.id
    )

    private val mapHbA1C = hashMapOf(
        "key" to "jenis",
        "value" to "HbA1C"
    )

    private val mapLBS = hashMapOf(
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

    private val queryUserId = listOf(
        mapIdUser
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

    fun isReadingDocument(): LiveData<Response> {
        val _response = MutableLiveData<Response>()
        _response.value = Response.Progress(true)
        viewModelScope.launch {
            savedPasien?.id?.let {
                _response.value = db.update(
                    Constant.KEY_PASIEN, it, "readingDocument", true
                )
            }
        }
        return _response
    }
}