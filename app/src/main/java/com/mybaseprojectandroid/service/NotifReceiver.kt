package com.mybaseprojectandroid.service

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.ui.splash.SplashActivity
import com.mybaseprojectandroid.utils.other.showLogAssert
import java.util.*

class NotifReceiver: BroadcastReceiver() {
    companion object {
        private const val REMIND_HOUR = 6
        private const val ALARM_ID = 101
    }

    override fun onReceive(context: Context, intent: Intent) {
        showLogAssert("AlarmReceiverTesting", "running....")
        val message = intent.extras?.getString("message")
        showLogAssert("onReceive message", "$message")
        showNotification(context, message)
        setReminder(context, message)
    }

    fun setReminder(context: Context, message: String?) {
        showLogAssert("setRemeinder", "true")
        showLogAssert("setRemeinder message", "$message")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this::class.java)
        intent.putExtra("message", message)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, REMIND_HOUR)
            set(Calendar.MINUTE, 30)
            set(Calendar.SECOND, 0)
        }

        if(calendar.time <= Calendar.getInstance().time) calendar.add(Calendar.DAY_OF_MONTH, 1)

        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0)
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, pendingIntent)
    }

    fun stopReminder(context: Context) {
        showLogAssert("stopReminder", "true")
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(context, this::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, 0)
        alarmManager.cancel(pendingIntent)
    }

    @SuppressLint("LaunchActivityFromNotification", "UnspecifiedImmutableFlag")
    private fun showNotification(context: Context, message: String?) {
        val channelId = "channel"
        val channelName = "reminder"

        val title = "Ayo beraktifitas lagi!!!"

        val notificationManagerCompat = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val notifyIntent = Intent(context, SplashActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        val notifyPendingIntent = PendingIntent.getActivity(
            context, 0, notifyIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentIntent(notifyPendingIntent)
            .setContentText(message)
            .setAutoCancel(true)

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
            builder.setChannelId(channelId)
            builder.setContentIntent(notifyPendingIntent)
            notificationManagerCompat.createNotificationChannel(channel)
        }
        val notification = builder.build()
        notificationManagerCompat.notify(ALARM_ID, notification)
    }

}