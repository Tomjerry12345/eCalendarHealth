package com.mybaseprojectandroid.ui.main.listPasien

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.Pasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant

class ListPasienViewModel(val db: FirebaseDatabase) : ViewModel() {

    val data: LiveData<Response> = liveData {
        val response =
            db.getAllData(
                Constant.KEY_PASIEN
            )
        emit(response)
    }

}