package com.mybaseprojectandroid.ui.main.listPasien.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ItemListPasienBinding
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveNavigationTo

class ListPasienAdapter(
    val list: List<PasienModel>,
    private val listPersenAktivitas: ArrayList<Int>
) :
    RecyclerView.Adapter<ListPasienAdapter.ViewHolder>() {

    inner class ViewHolder(private var binding: ItemListPasienBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pasien: PasienModel, position: Int, persen: Int) {

            binding.itemPasien = pasien
            binding.persen = "$persen %"

            binding.progresss.progress = persen

            binding.executePendingBindings()
            binding.number.text = position.plus(1).toString()
            val pos = position + 1
            if (pos % 2 == 0) binding.bgCard.setBackgroundResource(R.drawable.bg_gradient_horizontal_blue) else binding.bgCard.setBackgroundResource(
                R.drawable.bg_gradient_horizontal
            )

            val isReadingDocument = if (pasien.readingDocument) 100 else 0

            val bundle = bundleOf(
                Constant.KEY_NAMA_LENGKAP to pasien.namaLengkap,
                Constant.KEY_ID_USER to pasien.id,
                Constant.KEY_PERSEN to persen,
                Constant.KEY_IS_READING_DOCUMENT to isReadingDocument
            )

            binding.root.setOnClickListener {
                moveNavigationTo(
                    binding.root,
                    R.id.action_listPasienFragment_to_detailPasienFragment,
                    bundle
                )
            }

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val itemListPasienBinding = ItemListPasienBinding.inflate(layoutInflater, parent, false)
        return ViewHolder(itemListPasienBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        val persen = listPersenAktivitas[position]
        holder.bind(item, position, persen)
    }

    override fun getItemCount(): Int = list.size


}