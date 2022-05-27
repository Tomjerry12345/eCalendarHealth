package com.mybaseprojectandroid.ui.examples.widget

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.widget.CustomAlertDialog

class ExamplesWidgetViewModel : ViewModel() {

    fun onCustomAlertDialog(view: View) {
        MaterialAlertDialogBuilder(view.context)
            .setTitle("Pertanyaan")
            .setPositiveButton(
                "Add"
            ) { p0, p1 ->
                showLogAssert("getInputTxt", CustomAlertDialog.getInputTxt)
                showLogAssert("getInputDropdown", CustomAlertDialog.getInputDropdown)
            }
            .setNegativeButton("Cancel", null)
            .setView(CustomAlertDialog.getView(view.context))
            .show()
    }

    fun onGraphicChart(view: View) {
        showToast(view.context, "Belum tersedia")
    }

    class Factory() : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ExamplesWidgetViewModel() as T
        }
    }

}