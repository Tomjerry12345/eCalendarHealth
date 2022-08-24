package com.mybaseprojectandroid.ui.main.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.launch

class HistoryViewModel(val db: FirebaseDatabase) : ViewModel() {

    private val savedPasien = getSavedPasien()

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    fun setByMonth(month: Int) {
        showLogAssert("position", "$month")
        val mapIdUser = hashMapOf(
            "key" to "idUser",
            "value" to savedPasien?.id
        )

        val mapMonth = hashMapOf(
            "key" to "month",
            "value" to month
        )

        val listQuery = listOf(
            mapIdUser,
            mapMonth,

        )

        viewModelScope.launch {
            _response.value = db.getDataByQuery(
                Constant.KEY_AKTIVITAS,
                listQuery as List<HashMap<String, Any>>
            )
        }
    }

}