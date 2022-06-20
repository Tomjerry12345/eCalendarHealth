package com.mybaseprojectandroid.ui.main.ubahProfil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentUbahProfilBinding
import com.mybaseprojectandroid.ui.auth.register.RegisterViewModel
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.widget.DialogProgress


class UbahProfilFragment : Fragment(R.layout.fragment_ubah_profil) {

    private val viewModel: UbahProfilViewModel by viewModels {
        FactoryViewModel(UbahProfilViewModel(FirebaseDatabase()))
    }

    private lateinit var binding: FragmentUbahProfilBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUbahProfilBinding.bind(view)

        val pasien = getSavedPasien()

        binding.viewModel = viewModel

        viewModel.namaLengkap.value = pasien?.namaLengkap
        viewModel.alamat.value = pasien?.alamat
        viewModel.tanggalLahir.value = pasien?.tanggalLahir
        viewModel.lamaDiagnosaDm.value = pasien?.lamaDiagnosaDm

        val checekd = if (pasien?.pengobatan == "Oral") binding.rbOral.id else binding.rbInsulin.id
        binding.rgPengobatan.check(checekd)

        viewModel.pasienModel = pasien

        viewModel.pengobatan.value = pasien?.pengobatan
        viewModel.pendamping.value = pasien?.pendamping
//        viewModel.username.value = pasien?.username
        viewModel.password.value = pasien?.password

        binding.rgPengobatan.setOnCheckedChangeListener { radioGroup, i ->
            when (i) {
                binding.rbOral.id -> {
                    viewModel.pengobatan.value = "Oral"
                }

                binding.rbInsulin.id -> {
                    viewModel.pengobatan.value = "Insulin"
                }
            }

        }

        binding.tiTanggal.setOnClickListener {
            showDate()
        }

        getResponse()

    }

    private fun showDate() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
        val materialDatePicker: MaterialDatePicker<*> = materialDateBuilder.build()



        materialDatePicker.show(getParentFragmentManager(), "MATERIAL_DATE_PICKER")


        materialDatePicker.addOnPositiveButtonClickListener {

            binding.tiTanggal.setText(materialDatePicker.headerText)

        }
    }

    private fun getResponse() {
        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> TODO()
                is Response.Error -> showLogAssert("error", it.error)
                is Response.Progress -> {
                    val dialog = DialogProgress.initDialog(requireContext())

                    if (it.activated)
                        dialog.show()
                    else
                        dialog.hide()
                }
                is Response.Success -> {
                    SavedData.setObject(Constant.KEY_PASIEN, viewModel.pasienModel)
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
            }
        }
    }

}