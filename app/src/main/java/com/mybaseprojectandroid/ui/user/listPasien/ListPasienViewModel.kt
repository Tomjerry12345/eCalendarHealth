package com.mybaseprojectandroid.ui.user.listPasien

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.model.Pasien

class ListPasienViewModel(val rvItemPasien : RecyclerView) : ViewModel() {


    class Factory(val rvItemPasien : RecyclerView) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ListPasienViewModel(rvItemPasien) as T
        }
    }

    var listPasien = ArrayList<Pasien>()

    fun setData(){
        listPasien.add(Pasien("Fadhil","10%"))
        listPasien.add(Pasien("Fadhil","10%"))
        listPasien.add(Pasien("Fadhil","10%"))
        listPasien.add(Pasien("Fadhil","10%"))
        listPasien.add(Pasien("Fadhil","10%"))

        setList()
    }

    private fun setList() {
        val adapterr = ListPasienAdapter(listPasien)
        rvItemPasien.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterr
        }    }
}