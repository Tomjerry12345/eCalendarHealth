package com.mybaseprojectandroid.ui.user.profile

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentProfileBinding
import com.mybaseprojectandroid.ui.auth.AuthActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.system.moveIntentTo


class ProfileFragment : Fragment(R.layout.fragment_profile) {

    companion object {
        fun newInstance(): ProfileFragment = ProfileFragment()
    }

    private lateinit var binding: FragmentProfileBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentProfileBinding.bind(view)

        binding.keluar.setOnClickListener {
            SavedData.setBoolean(Constant.KEY_IS_LOGGIN, false)
            SavedData.setObject(Constant.KEY_PASIEN, null)
            moveIntentTo(requireActivity(), AuthActivity(), true)
        }
    }
}