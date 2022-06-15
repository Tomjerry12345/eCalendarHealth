package com.mybaseprojectandroid.ui.main.detailPasien

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentDetailPasienBinding
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.system.popNavigation


class DetailPasienFragment : Fragment(R.layout.fragment_detail_pasien) {

    private lateinit var binding: FragmentDetailPasienBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentDetailPasienBinding.bind(view)

        val namaLengkap = arguments?.getString(Constant.KEY_NAMA_LENGKAP)

        binding.nama.text = namaLengkap

        binding.back.setOnClickListener {
            popNavigation(requireView())
        }

    }

}