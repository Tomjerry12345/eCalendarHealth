package com.mybaseprojectandroid.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.datepicker.MaterialDatePicker
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentRegisterBinding
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.widget.DialogProgress

class RegisterFragment : Fragment(R.layout.fragment_register) {

    private val viewModel: RegisterViewModel by viewModels {
        FactoryViewModel(RegisterViewModel(FirebaseDatabase()))
    }

    private lateinit var binding: FragmentRegisterBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentRegisterBinding.bind(view)

        binding.viewModel = viewModel

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

        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> {
                    showLogAssert("succes register", "Succes")
                    viewModel.pasienModel?.id = it.data as String
                    SavedData.setBoolean(Constant.KEY_IS_LOGGIN, true)
                    SavedData.setObject(Constant.KEY_PASIEN, viewModel.pasienModel)
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
                is Response.Error -> {
                    showLogAssert("error register", it.error)
                }
                is Response.Success -> {

                }
                is Response.Progress -> {
                    val dialog = DialogProgress.initDialog(view.context)

                    if (it.activated)
                        dialog.show()
                    else
                        dialog.hide()
                }
            }
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