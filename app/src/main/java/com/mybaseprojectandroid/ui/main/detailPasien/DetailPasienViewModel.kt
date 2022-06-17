package com.mybaseprojectandroid.ui.main.detailPasien

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import kotlinx.coroutines.launch

class DetailPasienViewModel(val db: FirebaseDatabase) : ViewModel() {

    private val _responseAktivitas = MutableLiveData<Response>()
    val responseAktivitas: LiveData<Response> = _responseAktivitas

    private val _responseHbA1C = MutableLiveData<Response>()
    val responseHbA1C: LiveData<Response> = _responseHbA1C

    private val _responseLBS = MutableLiveData<Response>()
    val responseLBS: LiveData<Response> = _responseLBS

    fun setAktivitas(idUser: String) {

        val mapIdUser = hashMapOf(
            "key" to "idUser",
            "value" to idUser
        )

        val listQuery = listOf(
            mapIdUser
        )

        viewModelScope.launch {
            _responseAktivitas.value = db.getDataByQuery(Constant.KEY_AKTIVITAS, listQuery as List<HashMap<String, Any>>)
        }
    }

    fun setGrafikHbA1C(idUser: String) {

        val mapIdUser = hashMapOf(
            "key" to "idUser",
            "value" to idUser
        )

        val mapHbA1C = hashMapOf(
            "key" to "jenis",
            "value" to "HbA1C"
        )

        val listQueryHbA1C = listOf(
            mapIdUser,
            mapHbA1C
        )

        viewModelScope.launch {
            _responseHbA1C.value = db.getDataByQuery(
                Constant.KEY_PEMERIKSAAN,
                listQueryHbA1C as List<HashMap<String, Any>>
            )
        }
    }

    fun setGrafikLBS(idUser: String) {

        val mapIdUser = hashMapOf(
            "key" to "idUser",
            "value" to idUser
        )

        val mapLBS = hashMapOf(
            "key" to "jenis",
            "value" to "LBS"
        )

        val listQueryLBS = listOf(
            mapIdUser,
            mapLBS
        )

        viewModelScope.launch {
            _responseLBS.value = db.getDataByQuery(
                Constant.KEY_PEMERIKSAAN,
                listQueryLBS as List<HashMap<String, Any>>
            )
        }
    }

}