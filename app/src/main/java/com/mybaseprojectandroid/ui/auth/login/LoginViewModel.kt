package com.mybaseprojectandroid.ui.auth.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.AdminModel
import com.mybaseprojectandroid.utils.local.setSavedAdmin
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.checkEmpty
import kotlinx.coroutines.launch

class LoginViewModel(private val db: FirebaseDatabase) : ViewModel() {
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val response = MutableLiveData<Response>()

    fun onLogin() {
        try {
            val username = checkEmpty(username.value, "Username tidak boleh kosong")
            val password = checkEmpty(password.value, "Password tidak boleh kosong")

            response.value = Response.Progress(true)

            viewModelScope.launch {
//                if (username == "admin" && password == "55555") {
//                    val adminModel = AdminModel(
//                        username
//                    )
//                    setSavedAdmin(adminModel)
//                    response.value = Response.Success("Berhasil")
//                } else {
//                    response.value = db.login(Constant.KEY_PASIEN, username, password)
//                    response.value = Response.Progress(false)
//                }

                response.value = db.login(Constant.KEY_PASIEN, username, password)
                response.value = Response.Progress(false)

            }

        } catch (e: Exception) {
            response.value = Response.Progress(false)
            response.value = Response.Error(e.message.toString())
        }
    }
}