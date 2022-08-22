package com.mybaseprojectandroid.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Handler
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.NotificationUtil.createNotificationChannel
import java.text.SimpleDateFormat
import java.util.*

class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        showLogAssert("onReceive", "true")
        if (intent != null) {
            val validationTime = intent.getStringExtra("validationTime")
            showLogAssert("onReceive", getTimeNow())
            showLogAssert("validationTime", "$validationTime")
            // pengecekan dilakukan agar notifikasi tidak muncul berulang
            if (getTimeNow() == intent.getStringExtra("validationTime")) {
                showLogAssert("getTimeNow()", "true")
                if (context != null) createNotificationChannel(context)
            }

            if (intent.action == "android.intent.action.TIME_SET") {
                context?.stopService(Intent(context, AlarmService::class.java))

                // langkah ini dilakukan untuk memicu ulang agar service kembali menyala
                // setelah melakukan uji coba mengganti tanggal service mati
                Handler().postDelayed({ context?.startService(Intent(context, AlarmService::class.java)) }, 1000)
            }
        }
    }

    private fun getTimeNow(): String {
        val dateTimeMillis = System.currentTimeMillis()

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = dateTimeMillis

        return SimpleDateFormat("HH:mm:ss").format(calendar.time)
    }

}