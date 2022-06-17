package com.mybaseprojectandroid.ui.main.listPasien

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentListPasienBinding
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.ui.main.listPasien.adapter.ListPasienAdapter
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.popNavigation


class ListPasienFragment : Fragment(R.layout.fragment_list_pasien) {

    private lateinit var binding : FragmentListPasienBinding

    private val viewModel: ListPasienViewModel by viewModels {
        FactoryViewModel(ListPasienViewModel(FirebaseDatabase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListPasienBinding.bind(view)
        viewModel.data.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val data = querySnapshot.toObjects<PasienModel>()

                    val adapter1 = ListPasienAdapter(data)

                    binding.rvListPasien.apply {
                        adapter = adapter1
                        layoutManager = LinearLayoutManager(context)
                    }
                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }

        binding.backButton.setOnClickListener {
            popNavigation(requireView())
        }
    }
}