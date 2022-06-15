package com.mybaseprojectandroid.ui.examples.main

import android.view.View
import androidx.lifecycle.*
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.moveNavigationTo

class ExamplesMainViewModel : ViewModel() {

    fun onMoveToApi(view: View) {
        moveNavigationTo(view, R.id.action_examplesFragment_to_examplesApiFragment)
    }

    fun onMoveToFirebase(view: View) {
        moveNavigationTo(view, R.id.action_examplesFragment_to_examplesFirebaseFragment)
    }

    fun onMoveToRoom(view: View) {
        showToast(view.context, "Examples room belum tersedia")
    }

    fun onMoveToWidget(view: View) {
        moveNavigationTo(view, R.id.action_examplesFragment_to_examplesWidgetFragment)
    }

    fun onMoveToMidtrans(view: View) {
        showToast(view.context, "Examples midtrans belum tersedia")
    }

    fun onMoveToManipulasiFiles(view: View) {
        moveNavigationTo(view, R.id.action_examplesFragment_to_examplesManipulasiFilesFragment)
    }

    class Factory() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamplesMainViewModel() as T
        }
    }
}