package com.mybaseprojectandroid.ui.main.home.perawat

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentHomePerawatBinding
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.system.moveNavigationTo

class HomePerawatFragment : Fragment(R.layout.fragment_home_perawat) {

    companion object {
        fun newInstance() = HomePerawatFragment()
    }

    private val viewModel: HomePerawatViewModel by viewModels {
        FactoryViewModel(HomePerawatViewModel())
    }

    private lateinit var binding: FragmentHomePerawatBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomePerawatBinding.bind(view)

        binding.cardBtn.setOnClickListener {
            moveNavigationTo(requireView(), R.id.listPasienFragment)
        }
    }

}