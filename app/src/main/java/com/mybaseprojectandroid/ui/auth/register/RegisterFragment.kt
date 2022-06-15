package com.mybaseprojectandroid.ui.auth.register

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
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

        viewModel.response.observe(viewLifecycleOwner) {
            when(it) {
                is Response.Changed -> {}
                is Response.Error -> {
                    showLogAssert("error register", it.error)
                }
                is Response.Success -> {
                    showLogAssert("succes register", "Succes")
                    SavedData.setBoolean(Constant.KEY_IS_LOGGIN, true)
                    SavedData.setObject(Constant.KEY_PASIEN, viewModel.pasienModel)
                    moveIntentTo(requireActivity(), BaseActivity(), true)
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

}