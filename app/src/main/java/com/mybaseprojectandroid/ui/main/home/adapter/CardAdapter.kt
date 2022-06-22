package com.mybaseprojectandroid.ui.main.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemHomeBinding
import com.mybaseprojectandroid.model.CardItem
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.widget.RecyclerViewUtils

class CardAdapter(val list: List<CardItem>, private val recyclerListener: RecyclerViewUtils) :
    RecyclerView.Adapter<CardAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ItemHomeBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(card: CardItem) {
            binding.itemCard = card
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHomeBinding = ItemHomeBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemHomeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
        holder.itemView.setOnClickListener {
            when (item.title) {
                "Aktivitas" -> {
                    moveNavigationTo(holder.itemView, R.id.aktivitasFragment)
                }
                "Pemeriksaan" -> {
                    moveNavigationTo(holder.itemView, R.id.pemeriksaanFragment)
                }
                "Edukasi" -> {
                    recyclerListener.clicked()
                }
            }


        }
    }

    override fun getItemCount(): Int = list.size


}