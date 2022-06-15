package com.mybaseprojectandroid.ui.main.testimoni

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.databinding.ItemTestimoniBinding
import com.mybaseprojectandroid.model.Testimoni

class TestimoniAdapter(val list : ArrayList<Testimoni>) :
    RecyclerView.Adapter<TestimoniAdapter.ViewHolder>(){
    inner class ViewHolder(private var binding : ItemTestimoniBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(week : Testimoni){
            binding.itemTestimoni = week
            binding.executePendingBindings()


        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemWeekBinding = ItemTestimoniBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemWeekBinding)      }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.bind(item)    }

    override fun getItemCount(): Int = list.size

}