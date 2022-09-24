package com.mybaseprojectandroid.ui.main.listPasien

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.launch

class ListPasienViewModel(val db: FirebaseDatabase) : ViewModel() {

    val data: LiveData<Response> = liveData {
//        val response =
//            db.getAllData(
//                Constant.KEY_PASIEN
//            )
        val response =
            db.getDataByQuery(Constant.KEY_PASIEN, listOf(hashMapOf(
                "key" to "typeAkun",
                "value" to "pasien"
            )),)
        emit(response)
    }

    private val _isSucces = MutableLiveData<Boolean>()
    val isSucces: LiveData<Boolean> = _isSucces

    fun getAktivitas(idUser: String, month: Int?): LiveData<Response> {
        val response = MutableLiveData<Response>()

        viewModelScope.launch {
            response.value = db.getDataByQuery(Constant.KEY_AKTIVITAS, setListQuery(month, idUser))
        }

        return response
    }

    private fun setListQuery(month: Int?, idUser: String) =
        if (month != null) listOf(
            hashMapOf(
                "key" to "idUser",
                "value" to idUser
            ),
            hashMapOf(
                "key" to "update",
                "value" to true
            ),

            hashMapOf(
                "key" to "month",
                "value" to month
            )
        )
        else listOf(
            hashMapOf(
                "key" to "idUser",
                "value" to idUser
            ),
            hashMapOf(
                "key" to "update",
                "value" to true
            )
        )

    fun getDateBringWalking(idUser: String): LiveData<Response> {
//        showLogAssert("idUser", idUser)
        val response = MutableLiveData<Response>()

        val listQuery = listOf(
            hashMapOf(
                "key" to "idUser",
                "value" to idUser
            )
        )

        viewModelScope.launch {
            response.value = db.getDataByQuery(Constant.KEY_DATE_BRING_WALKING, listQuery)
        }

        return response
    }

    fun setIsSucces(value: Boolean) {
        _isSucces.value = value
    }

    fun setByMonth(month: Int): LiveData<Response> {
        val response = MutableLiveData<Response>()

        val mapMonth = hashMapOf(
            "key" to "month",
            "value" to month
        )

        val listQuery = listOf(
            mapMonth
        )

        viewModelScope.launch {
            response.value = db.getDataByQuery(
                Constant.KEY_AKTIVITAS,
                listQuery as List<HashMap<String, Any>>
            )
        }

        return response
    }

}