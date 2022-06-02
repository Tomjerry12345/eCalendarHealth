package com.mybaseprojectandroid.utils.other

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class FactoryViewModel(val viewModel: ViewModel) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return viewModel as T
    }
}