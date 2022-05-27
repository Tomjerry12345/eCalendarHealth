package com.mybaseprojectandroid.ui.examples.history

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemWeekBinding
import com.mybaseprojectandroid.model.Progress
import com.mybaseprojectandroid.model.Week

class HistoryAdapter(val listWeek : ArrayList<Week>,
                     val listProgress : ArrayList<Progress>) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>(){
    inner class ViewHolder(private var binding : ItemWeekBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(week : Week,position: Int){
            binding.itemWeek = week
            binding.executePendingBindings()
            binding.rvProgress
            val adapterr = week.progress?.let { ProgressAdapter(it) }
            binding.rvProgress.apply {
                layoutManager = GridLayoutManager(context,2)
                adapter = adapterr
            }
            var pos = position+1
            if (pos %2 == 0) binding.title.setBackgroundResource(R.drawable.bg_week_blue) else binding.title.setBackgroundResource(R.drawable.bg_week)

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemWeekBinding = ItemWeekBinding.inflate(layoutInflater,parent,false)
        return ViewHolder(itemWeekBinding)    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listWeek.get(position)
        holder.bind(item,position)


    }

    override fun getItemCount(): Int = listWeek.size
}