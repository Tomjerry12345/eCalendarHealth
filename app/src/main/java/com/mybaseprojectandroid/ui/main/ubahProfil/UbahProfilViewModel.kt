package com.mybaseprojectandroid.ui.main.ubahProfil

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.checkEmpty
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.launch

class UbahProfilViewModel(val db: FirebaseDatabase) : ViewModel() {

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

    fun onEdit() {
        try {
            val namaLengkap = checkEmpty(namaLengkap.value, "Nama lengkap tidak boleh kosong")
            val alamat = checkEmpty(alamat.value, "Alamat tidak boleh kosong")
            val tanggalLahir = checkEmpty(tanggalLahir.value, "Tanggal lahir tidak boleh kosong")
            val lamaDiagnosaDm =
                checkEmpty(lamaDiagnosaDm.value, "Lama diagnosa DM tidak boleh kosong")
            val pengobatan = checkEmpty(pengobatan.value, "Pengobatan tidak boleh kosong")
            val pendamping = checkEmpty(pendamping.value, "Pendamping/Keluarga tidak boleh kosong")
//            val username = checkEmpty(username.value, "Username tidak boleh kosong")
            val password = checkEmpty(password.value, "Password tidak boleh kosong")

            response.value = Response.Progress(true)

            pasienModel?.namaLengkap = namaLengkap
            pasienModel?.alamat = alamat
            pasienModel?.tanggalLahir = tanggalLahir
            pasienModel?.lamaDiagnosaDm = lamaDiagnosaDm
            pasienModel?.pengobatan = pengobatan
            pasienModel?.pendamping = pendamping
            pasienModel?.password = password

            showLogAssert("pasienModel", "$pasienModel")

            viewModelScope.launch {
                response.value = pasienModel?.id?.let {
                    pasienModel?.let { it1 ->
                        db.update(
                            Constant.KEY_PASIEN,
                            it, null, data = it1, msg = ""
                        )
                    }
                }
//                updateId(responseAdd)
                response.value = Response.Progress(false)
            }

        } catch (e: Exception) {
            response.value = Response.Progress(false)
            response.value = Response.Error(e.message.toString())
        }
    }
}