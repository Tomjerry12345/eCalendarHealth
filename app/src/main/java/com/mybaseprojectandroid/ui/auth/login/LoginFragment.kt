package com.mybaseprojectandroid.ui.auth.login

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.LoginFragmentBinding
import com.mybaseprojectandroid.ui.user.base.BaseActivity
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.system.moveNavigationTo

class LoginFragment : Fragment(R.layout.login_fragment) {

    private val viewModel: LoginViewModel by viewModels {
        FactoryViewModel(LoginViewModel(FirebaseDatabase()))
    }

    private lateinit var binding: LoginFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = LoginFragmentBinding.bind(view)

        binding.viewModel = viewModel

        binding.daftar.setOnClickListener {
            moveNavigationTo(requireView(), R.id.action_loginFragment_to_registerFragment)
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

//        binding.masuk.setOnClickListener {
//            requireActivity().apply {
//                startActivity(Intent(requireContext(), BaseActivity::class.java))
//                finish()
//            }
//        }
    }

}