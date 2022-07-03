package com.mybaseprojectandroid.ui.examples.widget

import android.view.View
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mybaseprojectandroid.utils.other.showToast

class ExamplesWidgetViewModel : ViewModel() {

    fun onCustomAlertDialog() {
//        MaterialAlertDialogBuilder(view.context)
//            .setTitle("Pertanyaan")
//            .setPositiveButton(
//                "Add"
//            ) { p0, p1 ->
//                showLogAssert("getInputTxt", CustomAlertDialog.getInputTxt)
//                showLogAssert("getInputDropdown", CustomAlertDialog.getInputDropdown)
//            }
//            .setNegativeButton("Cancel", null)
//            .setView(CustomAlertDialog.getView(view.context, R.layout.examples_custom_alert_dialog))
//            .show()
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