package com.mybaseprojectandroid.utils.widget

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.activity.ComponentActivity
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.system.moveIntentTo

object CustomAlertDialog {

    var btnClicked = false

    fun getView(activity: ComponentActivity, layout: Int): View? {
        val customAlertDialogView = LayoutInflater.from(activity)
            .inflate(layout, null, false)

        val btn = customAlertDialogView.findViewById<Button>(R.id.buttom)

        btn.setOnClickListener {
            moveIntentTo(activity, BaseActivity(), true)
        }

        return customAlertDialogView
    }
}