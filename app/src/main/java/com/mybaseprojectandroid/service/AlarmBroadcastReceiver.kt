package com.mybaseprojectandroid.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Handler
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.utils.local.getSavedContentMessageNotif
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.DateCustom.getHoursNow
import com.mybaseprojectandroid.utils.system.DateCustom.getMinuteNow
import com.mybaseprojectandroid.utils.system.DateCustom.getSecondNow
import com.mybaseprojectandroid.utils.system.DateCustom.getTimeNotif
import com.mybaseprojectandroid.utils.system.NotificationUtil.createNotificationChannel

@RequiresApi(Build.VERSION_CODES.O)
class AlarmBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        showLogAssert("onReceive", "true")
        if (intent != null) {
            val validationTime = intent.getStringExtra("validationTime")
            showLogAssert("onReceive", getTimeNotif())
            showLogAssert("validationTime", "$validationTime")
            // pengecekan dilakukan agar notifikasi tidak muncul berulang
            if (getTimeNotif() == intent.getStringExtra("validationTime")) {
                if (context != null) {
                    showLogAssert("notif is on", "true")
//                    createNotificationChannel(context)
                    showLogAssert("content testing", "${getSavedContentMessageNotif()}")

                }
            }

            if (intent.action == "android.intent.action.TIME_SET") {
                context?.stopService(Intent(context, AlarmService::class.java))

                // langkah ini dilakukan untuk memicu ulang agar service kembali menyala
                // setelah melakukan uji coba mengganti tanggal service mati
                Handler().postDelayed({ context?.startService(Intent(context, AlarmService::class.java)) }, 1000)
            }
        }
    }


//    private fun getTimeNow(): String {
//
////        val dateTimeMillis = System.currentTimeMillis()
////
////        val calendar = Calendar.getInstance()
////        calendar.timeInMillis = dateTimeMillis
////
////        return SimpleDateFormat("HH:mm:ss").format(calendar.time)
//        return "${getHoursNow()}:${getMinuteNow()}:${getSecondNow()}"
//    }

}