package com.mybaseprojectandroid.ui.main.ubahProfil

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentUbahProfilBinding
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

        val getUser = getSavedPasien()

        if (getUser?.typeAkun == "perawat") {
            binding.layoutDiagnosa.visibility = View.GONE
            binding.tvPengobatan.visibility = View.GONE
            binding.rgPengobatan.visibility = View.GONE
            binding.layoutPendamping.visibility = View.GONE
        }

        binding.viewModel = viewModel

        viewModel.namaLengkap.value = getUser?.namaLengkap
        viewModel.alamat.value = getUser?.alamat
        viewModel.tanggalLahir.value = getUser?.tanggalLahir
        viewModel.lamaDiagnosaDm.value = getUser?.lamaDiagnosaDm

        val checekd = if (getUser?.pengobatan == "Oral") binding.rbOral.id else binding.rbInsulin.id
        binding.rgPengobatan.check(checekd)

        viewModel.userModel = getUser

        viewModel.pengobatan.value = getUser?.pengobatan
        viewModel.pendamping.value = getUser?.pendamping
//        viewModel.username.value = getUser?.username
        viewModel.password.value = getUser?.password

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

        materialDatePicker.show(parentFragmentManager, "MATERIAL_DATE_PICKER")


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
                    SavedData.setObject(Constant.KEY_PASIEN, viewModel.userModel)
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
            }
        }
    }

}