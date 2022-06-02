package com.mybaseprojectandroid.ui.user.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemHomeBinding
import com.mybaseprojectandroid.model.CardItem
import com.mybaseprojectandroid.utils.system.moveNavigationTo

class CardAdapter (val list: ArrayList<CardItem>) : RecyclerView.Adapter<CardAdapter.ViewHolder>() {
    inner class ViewHolder(private var binding : ItemHomeBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(card : CardItem){
            binding.itemCard = card
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHomeBinding = ItemHomeBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemHomeBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            when(item.title){
                "Aktivitas" -> {
                    moveNavigationTo(holder.itemView, R.id.aktivitasFragment)
                }
                "Pemeriksaan" ->{
                    moveNavigationTo(holder.itemView, R.id.pemeriksaanFragment)
                }
            }


        }
    }

    override fun getItemCount(): Int  = list.size



}