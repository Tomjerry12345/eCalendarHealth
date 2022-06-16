package com.mybaseprojectandroid.ui.main.testimoni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybaseprojectandroid.databinding.FragmentTestimoniBinding

class TestimoniFragment : Fragment() {

    private lateinit var binding : FragmentTestimoniBinding
    private lateinit var viewModel: TestimoniViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTestimoniBinding.bind(view)
        viewModel = TestimoniViewModel(binding.rvListPasien)
        viewModel.setData()
        binding.lifecycleOwner = this
        binding.viewmodel = viewModel

    }

//    override fun onCreateView(
//        inflater: LayoutInflater, container: ViewGroup?,
//        savedInstanceState: Bundle?
//    ): View? {
//        // Inflate the layout for this fragment
//        binding = FragmentTestimoniBinding.inflate(inflater,container,false)
//        viewModel = TestimoniViewModel(binding.rvListPasien)
//        viewModel.setData()
//        binding.lifecycleOwner = this
//        binding.viewmodel = viewModel
//
//        return binding.root
//     }


}