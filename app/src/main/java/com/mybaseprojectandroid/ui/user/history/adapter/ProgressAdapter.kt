package com.mybaseprojectandroid.ui.user.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.databinding.ItemProgressBinding
import com.mybaseprojectandroid.model.Progress

class ProgressAdapter(val list: List<Progress>): RecyclerView.Adapter<ProgressAdapter.ViewHolder>()  {
    inner class ViewHolder(private var binding : ItemProgressBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(progress: Progress ){
            binding.itemProgress = progress
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHomeBinding = ItemProgressBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemHomeBinding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int  = list.size



}