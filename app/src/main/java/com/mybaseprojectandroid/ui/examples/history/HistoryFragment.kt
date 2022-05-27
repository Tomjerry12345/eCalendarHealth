package com.mybaseprojectandroid.ui.examples.history

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentHistoryBinding
import com.mybaseprojectandroid.ui.examples.home.HomeViewModel


class HistoryFragment : Fragment(R.layout.fragment_history) {

    companion object{
        fun newInstance() : HistoryFragment{
            return HistoryFragment()
        }
    }

    private lateinit var binding : FragmentHistoryBinding

    private val viewModel: HistoryViewModel by viewModels {
        HistoryViewModel.Factory(binding.rvWeek)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHistoryBinding.bind(view)
        viewModel.setData()
    }
}