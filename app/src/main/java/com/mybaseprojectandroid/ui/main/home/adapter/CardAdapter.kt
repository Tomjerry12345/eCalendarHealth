package com.mybaseprojectandroid.ui.main.home.adapter

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemHomeBinding
import com.mybaseprojectandroid.model.CardItem
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.widget.RecyclerViewUtils


class CardAdapter(private val context : Context,val list: List<CardItem>, private val recyclerListener: RecyclerViewUtils) :
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
                "Konsultasi" ->{
                    val intentWhatsapp = Intent(Intent.ACTION_VIEW)
                    val url = "https://chat.whatsapp.com/ICdRcxM0H5v3EGEuVFsLC0"
                    intentWhatsapp.data = Uri.parse(url)
                    intentWhatsapp.setPackage("com.whatsapp")
                    context.startActivity(intentWhatsapp)
                }
            }


        }
    }

    override fun getItemCount(): Int = list.size


}