package com.mybaseprojectandroid.ui.examples.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mybaseprojectandroid.R

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)
    }

    fun daftarBaru(view: View) {
        startActivity(Intent(this,RegisterActivity::class.java))
    }
}