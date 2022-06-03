package com.mybaseprojectandroid.ui.auth.login

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.checkEmpty
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.widget.DialogProgress
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
                response.value = db.login(Constant.KEY_PASIEN, username, password)
                Response.Progress(false)
            }

        } catch (e: Exception) {
            Response.Progress(false)
            Response.Error(e.message.toString())
        }
    }
}