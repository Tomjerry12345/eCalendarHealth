package com.mybaseprojectandroid.ui.main.pemeriksaan

import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentPemeriksaanBinding
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.DateCustom
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.system.popNavigation
import com.mybaseprojectandroid.utils.widget.DialogProgress


class PemeriksaanFragment : Fragment(R.layout.fragment_pemeriksaan) {

    private lateinit var binding: FragmentPemeriksaanBinding

    private val viewModel: PemeriksaanViewModel by viewModels {
        FactoryViewModel(PemeriksaanViewModel(FirebaseDatabase()))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentPemeriksaanBinding.bind(view)
        binding.viewModel = viewModel

        setDropdown()

        getResponse()

        binding.back.setOnClickListener {
            popNavigation(requireView())
        }

    }

    private fun getResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> {}
                is Response.Error -> {
                    showToast(requireContext(), it.error)
                }
                is Response.Success -> {
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
                is Response.Progress -> {
                    val dialog = DialogProgress.initDialog(requireContext())
                    if (it.activated)
                        dialog.show()
                    else
                        dialog.hide()
                }
            }
        }
    }

    private fun setDropdown() {
        val items = listOf("HbA1C", "LBS")
        (binding.tlJenis.editText as? AutoCompleteTextView)?.apply {
            setAdapter(ArrayAdapter(requireContext(), R.layout.item_dropdown, items))
            setOnItemClickListener { adapterView, view, i, l ->
                val getItem = adapterView.getItemAtPosition(i)
                viewModel._jenis.value = (getItem as String?).toString()
            }
        }
    }

}