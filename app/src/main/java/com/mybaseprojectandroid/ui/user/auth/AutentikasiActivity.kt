package com.mybaseprojectandroid.ui.user.auth

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.user.base.BaseActivity

class AutentikasiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_autentikasi)
    }

    fun daftar(view: View) {
        startActivity(Intent(this, RegisterActivity::class.java))
    }

    fun masuk(view: View) {
        startActivity(Intent(this, BaseActivity::class.java))
        finish()
    }
}