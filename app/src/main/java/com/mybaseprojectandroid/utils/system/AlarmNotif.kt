package com.mybaseprojectandroid.utils.system

import android.annotation.TargetApi
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import android.os.Build
import androidx.annotation.RequiresApi
import com.mybaseprojectandroid.service.AlarmReceiverTesting
import com.mybaseprojectandroid.service.AlarmService
import com.mybaseprojectandroid.utils.other.showLogAssert
import java.util.*


@RequiresApi(Build.VERSION_CODES.O)
@TargetApi(Build.VERSION_CODES.M)
class AlarmNotif(val context: Context) {

    private val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
    private val alarmPendingIntent = Intent(context, AlarmReceiverTesting::class.java).let { intent ->
        PendingIntent.getBroadcast(context, 0, intent, 0)
    }
//    private val alarmPendingIntent by lazy {
//        val intent = Intent(context, AlarmReceiverTesting::class.java)
//        PendingIntent.getBroadcast(context, 0, intent, 0)
//    }
    private val HOUR_TO_SHOW_PUSH = 3

    private val intent: Intent = Intent(context, AlarmService::class.java)

    fun startNotif() {
        val message = "Alarm service dimulai"
        context.startService(intent)
        setScheduleNotification()
        showLogAssert("notif message", message)
    }

    fun stopNotif() {
        context.stopService(intent)
    }

    private fun setScheduleNotification() {
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

    fun testingNotif() {
        showLogAssert("testingNotif", "running....")

        // Set the alarm to start at 8:30 a.m.
        val calendar: Calendar = Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 5)
            set(Calendar.MINUTE, 30)
        }

// setRepeating() lets you specify a precise custom interval--in this case,
// 20 minutes.
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis,
//            1000 * 60 * 5,
            alarmPendingIntent
        )

//        val calendar = GregorianCalendar.getInstance().apply {
//            if (get(Calendar.HOUR_OF_DAY) >= HOUR_TO_SHOW_PUSH) {
//                add(Calendar.DAY_OF_MONTH, 1)
//            }
//
//            set(Calendar.HOUR_OF_DAY, HOUR_TO_SHOW_PUSH)
//            set(Calendar.MINUTE, 45)
//            set(Calendar.SECOND, 0)
//            set(Calendar.MILLISECOND, 0)
//        }

//        alarmManager.setRepeating(
//            AlarmManager.RTC_WAKEUP,
//            calendar.timeInMillis,
//            AlarmManager.INTERVAL_DAY,
//            alarmPendingIntent
//        )
    }

}