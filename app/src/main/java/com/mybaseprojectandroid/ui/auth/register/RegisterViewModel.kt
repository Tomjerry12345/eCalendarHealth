package com.mybaseprojectandroid.ui.auth.register

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.UserModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.checkEmpty
import kotlinx.coroutines.launch

class RegisterViewModel(val db: FirebaseDatabase) : ViewModel() {

    val namaLengkap = MutableLiveData<String>()
    val alamat = MutableLiveData<String>()
    val tanggalLahir = MutableLiveData<String>()
    val lamaDiagnosaDm = MutableLiveData<String>()
    val pengobatan = MutableLiveData<String>()
    val pendamping = MutableLiveData<String>()
    val username = MutableLiveData<String>()
    val password = MutableLiveData<String>()

    val response = MutableLiveData<Response>()

    var userModel: UserModel? = null

    fun onRegister() {
        try {
            val namaLengkap = checkEmpty(namaLengkap.value, "Nama lengkap tidak boleh kosong")
            val alamat = checkEmpty(alamat.value, "Alamat tidak boleh kosong")
            val tanggalLahir = checkEmpty(tanggalLahir.value, "Tanggal lahir tidak boleh kosong")
            val lamaDiagnosaDm =
                checkEmpty(lamaDiagnosaDm.value, "Lama diagnosa DM tidak boleh kosong")
            val pengobatan = checkEmpty(pengobatan.value, "Pengobatan tidak boleh kosong")
            val pendamping = checkEmpty(pendamping.value, "Pendamping/Keluarga tidak boleh kosong")
            val username = checkEmpty(username.value, "Username tidak boleh kosong")
            val password = checkEmpty(password.value, "Password tidak boleh kosong")

            response.value = Response.Progress(true)

            userModel = UserModel(
                namaLengkap = namaLengkap,
                alamat = alamat,
                tanggalLahir = tanggalLahir,
                lamaDiagnosaDm = lamaDiagnosaDm,
                pengobatan = pengobatan,
                pendamping = pendamping,
                username = username,
                password = password,
                typeAkun = "pasien"
            )

            viewModelScope.launch {
                response.value = db.register(Constant.KEY_PASIEN, userModel!!, username)
//                updateId(responseAdd)
                response.value = Response.Progress(false)
            }

        } catch (e: Exception) {
            response.value = Response.Progress(false)
            response.value = Response.Error(e.message.toString())
        }
    }
}