package com.mybaseprojectandroid.ui.user.history

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentHistoryBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.ui.user.history.adapter.HistoryAdapter
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert


class HistoryFragment : Fragment(R.layout.fragment_history) {

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        FactoryViewModel(HistoryViewModel(FirebaseDatabase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHistoryBinding.bind(view)
        binding.viewmodel = viewModel

        setAdapter()

        getDataRiwayat()


    }

    private fun getDataRiwayat() {
        viewModel.dataRiwayat.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot
                    val riwayat = querySnapshot.toObjects<Aktivitas>()
                    showLogAssert("querySnapshot", "$riwayat")
                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun setAdapter() {
        val adapterr = HistoryAdapter(
            Constant.listWeek,
            Constant.listProgress
        )
        binding.rvWeek.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterr
        }
    }
}