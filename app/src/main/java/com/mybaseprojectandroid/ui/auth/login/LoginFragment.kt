package com.mybaseprojectandroid.ui.auth.login

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentLoginBinding
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.widget.DialogProgress

class LoginFragment : Fragment(R.layout.fragment_login) {

    private val viewModel: LoginViewModel by viewModels {
        FactoryViewModel(LoginViewModel(FirebaseDatabase()))
    }

    private lateinit var binding: FragmentLoginBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentLoginBinding.bind(view)

        val dialog = DialogProgress.initDialog(view.context)

        binding.viewModel = viewModel

        binding.daftar.setOnClickListener {
            moveNavigationTo(requireView(), R.id.action_loginFragment_to_registerFragment)
        }

        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                }
                is Response.Error -> {
                    dialog.dismiss()
                    showToast(view.context, it.error)
                }
                is Response.Success -> {
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
                is Response.Progress -> {
                    if (it.activated)
                        dialog.show()
                    else
                        dialog.dismiss()
                }
            }
        }

    }

}