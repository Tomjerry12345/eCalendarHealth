package com.mybaseprojectandroid.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.AlarmNotif

@RequiresApi(Build.VERSION_CODES.O)
class BootReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            showLogAssert("BootReceiver", "running....")
            AlarmNotif(context).testingNotif()
        }
    }
}