package com.mybaseprojectandroid.ui.auth.register

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.RegisterFragmentBinding
import com.mybaseprojectandroid.ui.user.base.BaseActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.system.moveNavigationTo

class RegisterFragment : Fragment(R.layout.register_fragment) {

    private val viewModel: RegisterViewModel by viewModels {
        FactoryViewModel(RegisterViewModel(FirebaseDatabase()))
    }

    private lateinit var binding: RegisterFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = RegisterFragmentBinding.bind(view)

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
//                    SavedData.setBoolean(Constant.KEY_IS_LOGGIN, true)
//                    SavedData.setObject(Constant.KEY_IS_LOGGIN, viewModel.pasienModel)
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
            }
        }
    }

}