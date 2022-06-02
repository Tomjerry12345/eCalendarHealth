package com.mybaseprojectandroid.ui.user.listPasien

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentListPasienBinding
import com.mybaseprojectandroid.ui.user.history.HistoryViewModel


class ListPasienFragment : Fragment(R.layout.fragment_list_pasien) {

    private lateinit var binding : FragmentListPasienBinding

    private val viewModel: ListPasienViewModel by viewModels {
        ListPasienViewModel.Factory(binding.rvListPasien)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListPasienBinding.bind(view)
        viewModel.setData()
    }
}