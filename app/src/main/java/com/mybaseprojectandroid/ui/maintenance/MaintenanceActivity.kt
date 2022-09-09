package com.mybaseprojectandroid.ui.maintenance

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton
import com.google.android.material.textview.MaterialTextView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.utils.other.showLogAssert


class MaintenanceActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_maintenance)

        val mtvMessage = findViewById<MaterialTextView>(R.id.mtv_message)
        val btnUpdate = findViewById<MaterialButton>(R.id.btnUpdate)

        val isVersion = intent.getStringExtra("isVersion")
        val message = intent.getStringExtra("message")

        showLogAssert("isVersion & message", "$isVersion $message")

        if (isVersion == "false") {
            btnUpdate.visibility = View.GONE
        }

        btnUpdate.setOnClickListener {
            val intent =
                Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=com.mybaseprojectandroid"))
            startActivity(intent)
        }

        mtvMessage.text = message
    }
}