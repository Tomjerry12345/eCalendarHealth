package com.mybaseprojectandroid.ui.user.history.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemProgressBinding
import com.mybaseprojectandroid.utils.system.getDrawable

class ProgressAdapter(private val sumBring: Int): RecyclerView.Adapter<ProgressAdapter.ViewHolder>()  {
    inner class ViewHolder(private var binding : ItemProgressBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(position: Int) {
//            binding.itemProgress = progress
            binding.item = "Bring walking ${position.plus(1)}"
            if (position > sumBring) {
                binding.ceklis.setImageDrawable(getDrawable(binding.root.context, R.drawable.ic_baseline_clear_24))
            }

            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemHomeBinding = ItemProgressBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemHomeBinding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(position)
    }

    override fun getItemCount(): Int  = 5



}