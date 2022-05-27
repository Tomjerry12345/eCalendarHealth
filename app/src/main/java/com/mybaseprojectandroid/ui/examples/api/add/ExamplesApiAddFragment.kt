package com.mybaseprojectandroid.ui.examples.api.add

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.ExamplesApiAddFragmentBinding
import com.mybaseprojectandroid.repository.ExamplesApiRepository
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.system.GetFilesFromStorage


class ExamplesApiAddFragment : Fragment(R.layout.examples_api_add_fragment) {

    private val viewModel: ExamplesApiAddViewModel by viewModels {
        ExamplesApiAddViewModel.Factory(ExamplesApiRepository())
    }

    private lateinit var binding: ExamplesApiAddFragmentBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = ExamplesApiAddFragmentBinding.bind(view)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel

        val observer = GetFilesFromStorage(requireActivity())

        lifecycle.addObserver(observer)

        binding.addImage.setOnClickListener {
            observer.setFile(Constant.MIME_ALL_IMAGE)
        }

        binding.addFile.setOnClickListener {
            observer.setFile(Constant.MIME_ALL_DOCUMENT)
        }

        getFile(observer)

    }

    private fun getFile(observer: GetFilesFromStorage) {
        val mimeType = observer.getMimeType()
        val inputStream = observer.getInputStream()

        if (mimeType == "image/jpeg" || mimeType == "image/png" || mimeType == "image/jpg") {
            val image = observer.getBitmap()
            binding.imageView.setImageBitmap(image)
            if (inputStream != null) {
                viewModel.imageFile = inputStream
                viewModel.mimeImage = mimeType
            }
        } else {
            if (inputStream != null) {
                viewModel.documentFile = inputStream
                viewModel.mimeFile = mimeType!!
            }
        }

    }

}