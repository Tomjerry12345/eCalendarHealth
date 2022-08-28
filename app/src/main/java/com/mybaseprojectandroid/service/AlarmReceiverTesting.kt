package com.mybaseprojectandroid.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.NotificationUtil

class AlarmReceiverTesting: BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        showLogAssert("AlarmReceiverTesting", "running....")
        if (context != null) {
            showLogAssert("createNotificationChannel", "running....")
//            NotificationUtil.createNotificationChannel(context, message)
        }
    }
}