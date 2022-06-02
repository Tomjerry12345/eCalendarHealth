package com.mybaseprojectandroid.utils.system

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController

fun moveNavigationTo(view: View, destination: Int, bundle: Bundle? = null) {
    view.findNavController().navigate(destination, bundle)
}

fun moveIntentTo(
    start: ComponentActivity,
    destination: AppCompatActivity,
    finish: Boolean = false
) {
    val intent = Intent(start, destination::class.java)
    start.apply {
        startActivity(intent)
        if (finish)
            finish()
    }
}
