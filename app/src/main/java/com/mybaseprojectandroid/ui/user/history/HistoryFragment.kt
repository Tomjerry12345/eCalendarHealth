package com.mybaseprojectandroid.ui.user.history

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentHistoryBinding
import com.mybaseprojectandroid.ui.user.home.HomeViewModel


class HistoryFragment : Fragment(R.layout.fragment_history) {

    companion object{
        fun newInstance() : HistoryFragment {
            return HistoryFragment()
        }
    }

    private lateinit var binding : FragmentHistoryBinding

    private lateinit var viewModel: HistoryViewModel


//    private val viewModel: HistoryViewModel by viewModels {
//        HistoryViewModel.Factory(binding.rvWeek)


//    }

//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        binding = FragmentHistoryBinding.bind(view)
//        viewModel.setData()
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHistoryBinding.inflate(inflater,container,false)
        viewModel = HistoryViewModel(binding.rvWeek)
        viewModel.setData()
        binding.setLifecycleOwner(this)
        binding.viewmodel =viewModel

        return binding.root

    }
}