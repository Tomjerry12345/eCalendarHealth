package com.mybaseprojectandroid.utils.widget

import android.app.AlertDialog
import android.content.Context
import dmax.dialog.SpotsDialog

object DialogProgress {
    fun initDialog(context: Context): AlertDialog {
        return SpotsDialog.Builder()
            .setContext(context)
            .setMessage("Mohon tunggu....")
            .setCancelable(false)
            .build()
    }
}