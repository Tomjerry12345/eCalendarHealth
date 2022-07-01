package com.mybaseprojectandroid.ui.main.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentProfileBinding
import com.mybaseprojectandroid.ui.auth.AuthActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.local.setSavedAdmin
import com.mybaseprojectandroid.utils.local.setSavedPasien
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.system.moveIntentTo
import com.mybaseprojectandroid.utils.system.moveNavigationTo


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        val pasien = getSavedPasien()

        binding.txtNameInisial.text = pasien?.namaLengkap?.get(0)?.toString()
        binding.tvNamaLengkap.text = pasien?.namaLengkap

        binding.keluar.setOnClickListener {
            SavedData.setBoolean(Constant.KEY_IS_LOGGIN, false)
            setSavedPasien(null)
            setSavedAdmin(null)
            moveIntentTo(requireActivity(), AuthActivity(), true)
        }
        binding.tvUbahProfil.setOnClickListener{
            moveNavigationTo(requireView(),R.id.ubahProfilFragment, )
        }
    }
}