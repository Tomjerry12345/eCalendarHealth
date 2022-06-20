package com.mybaseprojectandroid.ui.main.listPasien

import androidx.lifecycle.*
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.launch

class ListPasienViewModel(val db: FirebaseDatabase) : ViewModel() {

    val data: LiveData<Response> = liveData {
        val response =
            db.getAllData(
                Constant.KEY_PASIEN
            )
        emit(response)
    }

    private val _isSucces = MutableLiveData<Boolean>()
    val isSucces: LiveData<Boolean> = _isSucces

    fun getAktivitas(idUser: String): LiveData<Response> {
        showLogAssert("idUser", idUser)
        val response = MutableLiveData<Response>()

        val listQuery = listOf(
            hashMapOf(
                "key" to "idUser",
                "value" to idUser
            ),
            hashMapOf(
                "key" to "update",
                "value" to true
            )
        )

        viewModelScope.launch {
            response.value = db.getDataByQuery(Constant.KEY_AKTIVITAS, listQuery)
        }

        return response
    }

    fun setIsSucces(value: Boolean) {
        _isSucces.value = value
    }

}