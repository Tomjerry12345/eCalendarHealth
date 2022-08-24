package com.mybaseprojectandroid.ui.main.history

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentHistoryBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.ui.main.history.adapter.HistoryAdapter
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.DateCustom

@RequiresApi(Build.VERSION_CODES.O)
class HistoryFragment : Fragment(R.layout.fragment_history) {

    companion object {
        fun newInstance() = HistoryFragment()
    }

    private lateinit var binding: FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        FactoryViewModel(HistoryViewModel(FirebaseDatabase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHistoryBinding.bind(view)
        binding.viewmodel = viewModel

        setDropdown()
    }

    private fun getDataRiwayat() {
        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot
                    val riwayat = querySnapshot.toObjects<Aktivitas>()

                    showLogAssert("riwayat", "$riwayat")

                    val sorting = riwayat.sortedBy { riwayat ->
                        riwayat.week
                    }

                    setAdapter(sorting)


                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun setAdapter(riwayat: List<Aktivitas>) {
        val adapterr = HistoryAdapter(
            riwayat
        )
        binding.rvWeek.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = adapterr
        }
    }

    private fun setDropdown() {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, DateCustom.listNameMonth)
        (binding.dropdownMonth.editText as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setOnItemClickListener { adapterView, view, i, l ->
                viewModel.setByMonth(i.plus(1))
                getDataRiwayat()
            }
        }
    }
}