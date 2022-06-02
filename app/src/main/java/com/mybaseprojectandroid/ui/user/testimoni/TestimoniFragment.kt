package com.mybaseprojectandroid.ui.user.testimoni

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentTestimoniBinding

class TestimoniFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private lateinit var binding : FragmentTestimoniBinding
    private lateinit var viewModel: TestimoniViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTestimoniBinding.inflate(inflater,container,false)
        viewModel = TestimoniViewModel(binding.rvListPasien)
        viewModel.setData()
        binding.setLifecycleOwner(this)
        binding.viewmodel = viewModel

        return binding.root
     }


}