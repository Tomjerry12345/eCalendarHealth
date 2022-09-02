package com.mybaseprojectandroid.ui.main.home.pasien

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
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
        "value" to "Gula Darah Sewaktu"
    )

    private val listQueryHbA1C = listOf(
        mapIdUser,
        mapHbA1C
    )

    private val listQueryLBS = listOf(
        mapIdUser,
        mapLBS
    )

    private val listQueryData = listOf(
        mapIdUser,
        hashMapOf(
            "key" to "update",
            "value" to true
        )
    )

    val data: LiveData<Response> = liveData {
        val response =
            db.getDataByQuery(
                Constant.KEY_AKTIVITAS,
                listQueryData as List<HashMap<String, Any>>
            )

        emit(response)
    }

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

    fun updateAktivitas(aktivitasModel: Aktivitas): LiveData<Response> {
        val _response = MutableLiveData<Response>()

        viewModelScope.launch {
            _response.value =
                aktivitasModel.id?.let {
                    db.update(Constant.KEY_AKTIVITAS,
                        it, null, aktivitasModel)
                }
        }
        return _response
    }
}