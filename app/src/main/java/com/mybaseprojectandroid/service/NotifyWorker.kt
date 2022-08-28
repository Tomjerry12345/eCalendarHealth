package com.mybaseprojectandroid.service

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.NotificationUtil

class NotifyWorker(val context: Context, params: WorkerParameters) :
    Worker(context, params) {
    override fun doWork(): Result {
        // Method to trigger an instant notification
        showLogAssert("worker", "running....")
        val message =
            inputData.getString("MESSAGE") ?: return Result.failure()
        NotificationUtil.createNotificationChannel(context, message)
        return Result.success()
        // (Returning RETRY tells WorkManager to try this task again
        // later; FAILURE says not to try again.)
    }
}