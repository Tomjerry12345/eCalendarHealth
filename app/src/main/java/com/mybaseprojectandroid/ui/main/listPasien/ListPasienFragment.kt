package com.mybaseprojectandroid.ui.main.listPasien

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.activity.addCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentListPasienBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.DateBringWalking
import com.mybaseprojectandroid.model.UserModel
import com.mybaseprojectandroid.ui.main.listPasien.adapter.ListPasienAdapter
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.DateCustom
import com.mybaseprojectandroid.utils.system.ExcellUtils
import com.mybaseprojectandroid.utils.system.FilesUtils
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.widget.DialogProgress


class ListPasienFragment : Fragment(R.layout.fragment_list_pasien) {

    private lateinit var binding: FragmentListPasienBinding

    private val viewModel: ListPasienViewModel by viewModels {
        FactoryViewModel(ListPasienViewModel(FirebaseDatabase()))
    }

    private var i = 0
    var listPersenAktivitas = ArrayList<Int>()
    private lateinit var dataUserModel: List<UserModel>
    private lateinit var dateBringWalking: ArrayList<List<DateBringWalking>>

    private lateinit var dialog: AlertDialog

    @RequiresApi(Build.VERSION_CODES.O)
    private var resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            data?.data?.also { uri ->
                val excellUtils = ExcellUtils(requireActivity(), dataUserModel, dateBringWalking)
                excellUtils.createExcel(excellUtils.createWorkbook(), requireContext(), uri)
            }
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListPasienBinding.bind(view)

        dialog = DialogProgress.initDialog(requireContext())

        dateBringWalking = ArrayList()

        dialog.show()

        setDropdown()

        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {

                    viewModel.setIsSucces(false)

                    val querySnapshot = it.data as QuerySnapshot

                    dataUserModel = querySnapshot.toObjects()

                    getAktivitas(null)
                }
                is Response.Error -> {
                    showLogAssert("error", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }

        viewModel.isSucces.observe(viewLifecycleOwner) {
            if (it) {
                dialog.hide()
                setAdapter()
                i = 0
                listPersenAktivitas = ArrayList()
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

    private fun setAdapter() {
        val adapter1 = ListPasienAdapter(
            dataUserModel,
            listPersenAktivitas
        )

        binding.rvListPasien.apply {
            adapter = adapter1
            layoutManager = LinearLayoutManager(context)
        }
    }

    fun getAktivitas(month: Int?) {
        Handler(Looper.getMainLooper()).postDelayed({
            dataUserModel[i].id?.let {
                viewModel.getAktivitas(it, month).observe(viewLifecycleOwner) { respons ->
                    when (respons) {
                        is Response.Changed -> {
                            val queryRespons = respons.data as QuerySnapshot

                            val aktivitas = queryRespons.toObjects<Aktivitas>()
                            showLogAssert("aktivitas", "$aktivitas")

                            if (aktivitas.isNotEmpty()) {
                                val hasilPersen = (aktivitas[0].sumWeekBring!! * 100) / 5
                                listPersenAktivitas.add(hasilPersen)
                            } else {
                                listPersenAktivitas.add(0)
                            }

                            if (i == this.dataUserModel.size - 1) {
                                viewModel.setIsSucces(true)
                            } else {
                                i += 1
                                getAktivitas(month)
                            }
                        }
                        is Response.Error -> TODO()
                        is Response.Progress -> TODO()
                        is Response.Success -> TODO()
                    }
                }

                viewModel.getDateBringWalking(it).observe(viewLifecycleOwner) { respons ->
                    when (respons) {
                        is Response.Changed -> {
                            val queryRespons = respons.data as QuerySnapshot

                            val dateBringWalking: List<DateBringWalking> = queryRespons.toObjects()
                            showLogAssert("dateBringWalking", "$dateBringWalking")

                            if (dateBringWalking.isNotEmpty()) {
                                this.dateBringWalking.add(dateBringWalking)
                            }
                        }
                        is Response.Error -> TODO()
                        is Response.Progress -> TODO()
                        is Response.Success -> TODO()
                    }
                }
            }

        }, 100)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun exportExcell() {
        if (dataUserModel.isNotEmpty()) {
            val filesUtils = FilesUtils()
            filesUtils.createFile(resultLauncher, "data", requireActivity())

        } else {
            binding.btnExport.isEnabled = false
        }
    }

    fun back() {
        moveNavigationTo(requireView(), R.id.action_listPasienFragment_to_baseFragment)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDropdown() {
        val adapter =
            ArrayAdapter(requireContext(), R.layout.item_dropdown, DateCustom.listNameMonth)
        (binding.dropdownMonth.editText as? AutoCompleteTextView)?.apply {
            setAdapter(adapter)
            setOnItemClickListener { adapterView, view, i, l ->
                dialog.show()
               getAktivitas(i.plus(1))
            }
        }
    }
}