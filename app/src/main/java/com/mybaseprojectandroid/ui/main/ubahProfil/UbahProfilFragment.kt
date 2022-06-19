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
import com.mybaseprojectandroid.utils.other.FactoryViewModel


class UbahProfilFragment : Fragment(R.layout.fragment_ubah_profil) {

    private val viewModel: RegisterViewModel by viewModels {
        FactoryViewModel(RegisterViewModel(FirebaseDatabase()))
    }

    private lateinit var binding: FragmentUbahProfilBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentUbahProfilBinding.bind(view)


        binding.tiTanggal.setOnClickListener {
            showDate()
        }

    }

    private fun showDate() {
        val materialDateBuilder: MaterialDatePicker.Builder<*> =
            MaterialDatePicker.Builder.datePicker()
        val materialDatePicker: MaterialDatePicker<*> = materialDateBuilder.build()



        materialDatePicker.show(getParentFragmentManager() , "MATERIAL_DATE_PICKER")


        materialDatePicker.addOnPositiveButtonClickListener {

            binding.tiTanggal.setText(materialDatePicker.headerText)

        }
    }


}