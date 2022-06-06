package com.mybaseprojectandroid.ui.user.pemeriksaan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.Pemeriksaan
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.checkEmpty
import kotlinx.coroutines.launch

class PemeriksaanViewModel(private val db: FirebaseDatabase) : ViewModel() {

    val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    val _jenis = MutableLiveData<String>()
    private val jenis: LiveData<String> = _jenis

    val _nilai = MutableLiveData<String>()
    val nilai: LiveData<String> = _nilai

    private val _response = MutableLiveData<Response>()
    val response: LiveData<Response> = _response

    val savedPasien = getSavedPasien()

    fun onAddPemeriksaan() {
        try {
            val date = checkEmpty(date.value, "Date tidak boleh kosong")
            val jenis = checkEmpty(jenis.value, "Jenis tidak boleh kosong")
            val nilai = checkEmpty(nilai.value, "Nilai tidak boleh kosong")

            _response.value = Response.Progress(true)

            val pemeriksaan = Pemeriksaan(
                idUser = savedPasien.id,
                tanggal = date,
                jenis = jenis,
                nilai = nilai.toFloat()
            )

            viewModelScope.launch {
                _response.value = db.addData(Constant.KEY_PEMERIKSAAN, pemeriksaan)
                Response.Progress(false)
            }

        } catch (e: Exception) {
            Response.Progress(false)
            Response.Error(e.message.toString())
        }
    }

    fun onAddTanggal() {
//        MaterialDatePicker.Builder.datePicker()
//            .setTitleText("Select date")
//            .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
//            .build()
//            .show(this.parentFragmentManager, "")
    }

}