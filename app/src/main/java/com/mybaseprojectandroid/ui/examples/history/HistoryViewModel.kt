package com.mybaseprojectandroid.ui.examples.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.model.Progress
import com.mybaseprojectandroid.model.Week
import com.mybaseprojectandroid.ui.examples.home.CardAdapter
import com.mybaseprojectandroid.ui.examples.home.HomeViewModel

class HistoryViewModel(val rvWeekItem : RecyclerView) : ViewModel() {
    class Factory(val rvWeekItem : RecyclerView) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HistoryViewModel(rvWeekItem) as T
        }
    }

    var listProgress = ArrayList<Progress>()
    var listWeek = ArrayList<Week>()

    fun setData(){
        listProgress.add(Progress("Brisk Walking 1"))
        listProgress.add(Progress("Brisk Walking 2"))
        listProgress.add(Progress("Brisk Walking 3"))
        listProgress.add(Progress("Brisk Walking 4"))
        listProgress.add(Progress("Brisk Walking 5"))

        listWeek.add(Week("Minggu 1",listProgress))
        listWeek.add(Week("Minggu 2",listProgress))
        listWeek.add(Week("Minggu 3",listProgress))
        listWeek.add(Week("Minggu 4",listProgress))

        setList()


    }

    private fun setList() {
        val adapterr = HistoryAdapter(listWeek,listProgress)
        rvWeekItem.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterr
        }
    }


}