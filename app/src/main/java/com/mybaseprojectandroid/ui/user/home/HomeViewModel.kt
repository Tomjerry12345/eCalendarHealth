package com.mybaseprojectandroid.ui.user.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.model.CardItem

class HomeViewModel(val rvCardItem : RecyclerView) : ViewModel() {
    class Factory(val rvCardItem : RecyclerView) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return HomeViewModel(rvCardItem) as T
        }
    }

    var listCardItem = ArrayList<CardItem>()

    fun setData(){
        listCardItem.add(CardItem(R.drawable.item_card1,"Aktivitas","Lorem ipsum dolor sit consectetur adipis",R.drawable.card1))
        listCardItem.add(CardItem(R.drawable.item_card2,"Edukasi","Lorem ipsum dolor sit consectetur adipis",R.drawable.card2))
        listCardItem.add(CardItem(R.drawable.item_card3,"Konsultasi","Lorem ipsum dolor sit consectetur adipis",R.drawable.card3))
        listCardItem.add(CardItem(R.drawable.item_card4,"Pemeriksaan","Lorem ipsum dolor sit consectetur adipis",R.drawable.card4))

        setRecCard()
    }

    private fun setRecCard() {
        val adapterr = CardAdapter(listCardItem)
        rvCardItem.apply {
            layoutManager = GridLayoutManager(context,2)
            adapter = adapterr
        }

    }


}