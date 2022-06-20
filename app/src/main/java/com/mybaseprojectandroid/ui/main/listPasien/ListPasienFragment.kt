package com.mybaseprojectandroid.ui.main.listPasien

import android.os.Bundle
import android.view.View
import androidx.activity.addCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentListPasienBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.PasienModel
import com.mybaseprojectandroid.ui.main.listPasien.adapter.ListPasienAdapter
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.ExcellUtils
import com.mybaseprojectandroid.utils.system.moveNavigationTo


class ListPasienFragment : Fragment(R.layout.fragment_list_pasien) {

    private lateinit var binding: FragmentListPasienBinding

    private val viewModel: ListPasienViewModel by viewModels {
        FactoryViewModel(ListPasienViewModel(FirebaseDatabase()))
    }

    private var i = 0
    val listPersenAktivitas = ArrayList<Int>()
    private lateinit var dataPasienModel: List<PasienModel>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListPasienBinding.bind(view)
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {

                    viewModel.setIsSucces(false)

                    val querySnapshot = it.data as QuerySnapshot

                    dataPasienModel = querySnapshot.toObjects()

                    dataPasienModel.forEach { pasien ->
                        viewModel.getAktivitas(pasien.id!!).observe(viewLifecycleOwner) { respons ->
                            when (respons) {
                                is Response.Changed -> {
                                    val queryRespons = respons.data as QuerySnapshot

                                    val aktivitas = queryRespons.toObjects<Aktivitas>()

                                    showLogAssert("aktivitas", "$aktivitas")
                                    showLogAssert("i", "$i")

                                    var hasilPersen = 0

                                    if (aktivitas.isNotEmpty()) {
                                        hasilPersen = (aktivitas[0].sumWeekBring!! * 100) / 5
                                    }

                                    listPersenAktivitas.add(hasilPersen)

                                    if (i == dataPasienModel.size - 1) {
                                        viewModel.setIsSucces(true)
                                    }
                                    i += 1
                                }
                                is Response.Error -> TODO()
                                is Response.Progress -> TODO()
                                is Response.Success -> TODO()
                            }
                        }

                    }

                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }

        viewModel.isSucces.observe(viewLifecycleOwner) {
            showLogAssert("isSucces", "$it")
            if (it) {
                showLogAssert("listPersenAktivitas", "$listPersenAktivitas")
                val adapter1 = ListPasienAdapter(dataPasienModel, listPersenAktivitas)

                binding.rvListPasien.apply {
                    adapter = adapter1
                    layoutManager = LinearLayoutManager(context)
                }
            }
        }

        requireActivity().onBackPressedDispatcher.addCallback(requireActivity()) {
            // Handle the back button event
            back()
        }

        binding.backButton.setOnClickListener {
            back()
        }

        binding.btnExport.setOnClickListener {
            exportExcell()
        }
    }

    private fun exportExcell() {
        if (dataPasienModel.isNotEmpty()) {
            showLogAssert("dataPasienModel", "$dataPasienModel")
            val excellUtils = ExcellUtils(requireActivity(), dataPasienModel)
            excellUtils.createExcel(excellUtils.createWorkbook())
        } else {
            binding.btnExport.isEnabled = false
        }

    }

    fun back() {
        moveNavigationTo(requireView(), R.id.action_listPasienFragment_to_baseFragment)
    }
}