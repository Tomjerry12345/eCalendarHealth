package com.mybaseprojectandroid.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.mybaseprojectandroid.utils.other.showLogAssert

@RequiresApi(Build.VERSION_CODES.O)
class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            showLogAssert("BootReceiver", "running....")
            val message = intent.extras?.getString("message")
           NotifReceiver().setReminder(context, message)
        }
    }
}