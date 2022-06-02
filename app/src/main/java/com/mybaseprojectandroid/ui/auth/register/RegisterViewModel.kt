package com.mybaseprojectandroid.ui.auth.register

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.checkEmpty
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.widget.DialogProgress
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

    var pasienModel: PasienModel? = null

    fun onRegister(view: View) {
        val dialog = DialogProgress.initDialog(view.context)

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

            dialog.show()

            pasienModel = PasienModel(
                namaLengkap = namaLengkap,
                alamat = alamat,
                tanggalLahir = tanggalLahir,
                lamaDiagnosaDm = lamaDiagnosaDm,
                pengobatan = pengobatan,
                pendamping = pendamping,
                username = username,
                password = password
            )

            viewModelScope.launch {
                val responseAdd = db.addData(Constant.KEY_PASIEN, pasienModel!!)
                updateId(responseAdd)
                dialog.hide()
            }

        } catch (e: Exception) {
            dialog.hide()
            showToast(view.context, e.message.toString())
        }
    }

    private suspend fun updateId(responseRegister: Response) {
        when (responseRegister) {
            is Response.Changed -> {
                val id = responseRegister.data as String
                response.value = db.update(Constant.KEY_PASIEN, id, "id", id)
            }
            is Response.Error -> showLogAssert("error responseAdd", responseRegister.error)
            is Response.Success -> TODO()
        }
    }
}