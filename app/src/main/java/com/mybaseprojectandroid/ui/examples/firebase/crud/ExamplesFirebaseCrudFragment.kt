package com.mybaseprojectandroid.ui.examples.firebase.crud

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybaseprojectandroid.R

class ExamplesFirebaseCrudFragment : Fragment() {

    companion object {
        fun newInstance() = ExamplesFirebaseCrudFragment()
    }

    private lateinit var viewModel: ExamplesFirebaseCrudViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.examples_firebase_crud_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(ExamplesFirebaseCrudViewModel::class.java)
        // TODO: Use the ViewModel
    }

}