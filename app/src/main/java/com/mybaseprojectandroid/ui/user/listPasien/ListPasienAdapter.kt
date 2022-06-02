package com.mybaseprojectandroid.ui.user.listPasien

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemListPasienBinding
import com.mybaseprojectandroid.model.Pasien

class ListPasienAdapter(val list : ArrayList<Pasien>) :
    RecyclerView.Adapter<ListPasienAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding : ItemListPasienBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(week : Pasien, position: Int){
            binding.itemPasien = week
            binding.executePendingBindings()
            binding.number.text = position.plus(1).toString()
            var pos = position+1
            if (pos %2 == 0) binding.bgCard.setBackgroundResource(R.drawable.bg_gradient_horizontal_blue) else binding.bgCard.setBackgroundResource(
                R.drawable.bg_gradient_horizontal)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListPasienBinding = ItemListPasienBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemListPasienBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list.get(position)
        holder.bind(item,position)    }
    override fun getItemCount(): Int = list.size


}