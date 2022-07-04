package com.mybaseprojectandroid.ui.main.tentang

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentTentangBinding
import com.mybaseprojectandroid.databinding.FragmentUbahProfilBinding
import com.mybaseprojectandroid.utils.system.popNavigation


class TentangFragment : Fragment(R.layout.fragment_tentang) {

    private lateinit var binding: FragmentTentangBinding



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentTentangBinding.bind(view)

        binding.back.setOnClickListener {
            popNavigation(requireView())
        }

    }
}