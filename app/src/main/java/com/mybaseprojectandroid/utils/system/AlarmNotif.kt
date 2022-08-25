package com.mybaseprojectandroid.utils.system

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.CountDownTimer
import com.mybaseprojectandroid.service.*
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import java.util.concurrent.TimeUnit

@TargetApi(Build.VERSION_CODES.M)
class AlarmNotif(val context: Context ) {

    private val intent: Intent = Intent(context, AlarmService::class.java)

    fun startNotif() {
        val countDownTimer = object : CountDownTimer(6000, 1000) {
            override fun onFinish() {

                // alarm berulang
                var message = "Alarm service dimulai"
                context.startService(intent)

//                // alarm sekali jalan
//                message = "Alarm akan menyala dalam hitungan waktu mundur"
                setScheduleNotification()
//
////                txt_counter.text = message
//                showToast(context, message)
                showLogAssert("notif message", message)
            }

            override fun onTick(millisUntilFinished: Long) {
                val time = TimeUnit.SECONDS.convert(millisUntilFinished / 1000, TimeUnit.SECONDS)
                    .toString()
//                showToast(context, time)
            }
        }
        countDownTimer.start()
    }

    fun stopNotif() {
        context.stopService(intent)
    }

    fun setScheduleNotification() {
        // membuat objek intent yang mana akan menjadi target selanjutnya
        // bisa untuk berpindah halaman dengan dan tanpa data.
//        val intentMove = Intent(context, BaseActivity::class.java)
//        intent.putExtra("key", "value")

        // membuat objek PendingIntent yang berguna sebagai penampung intent dan aksi yang akan dikerjakan
        val requestCode = 0
//        val pendingIntent =
//            PendingIntent.getActivity(context, requestCode, intentMove, 0)

        // membuat objek AlarmManager untuk melakukan pendataran alarm yang akan dijadwalkan
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // kita buat alarm yang dapat berfungsi walaupun dalam kondisi hp idle dan tepat waktu
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            System.currentTimeMillis() + 5000,
            null
//            pendingIntent
        )
    }
}