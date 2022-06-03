package com.mybaseprojectandroid.utils.system

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.mybaseprojectandroid.utils.other.showLogAssert
import kotlinx.coroutines.*
import java.util.concurrent.TimeUnit

object Timer {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.Default + job)

    private var totalSeconds = TimeUnit.MINUTES.toSeconds(2)

    private val _getResponseTimer = MutableLiveData<Response>()
    val getResponseTimer: LiveData<Response> = _getResponseTimer

    private fun startCoroutineTimer(
        delayMillis: Long,
        repeatMillis: Long,
        action: () -> Unit
    ) = scope.launch(Dispatchers.IO) {
        delay(delayMillis)
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }

    private lateinit var timer: Job

    fun startTimer() {
        _getResponseTimer.postValue(Response.Finish(false))
        timer = startCoroutineTimer(delayMillis = 0, repeatMillis = 1000) {
            processTimer()
        }
    }

    fun cancelTimer() {
        timer.cancel()
    }

    private fun processTimer() {
        val time = String.format(
            "%02d:%02d",
            TimeUnit.SECONDS.toMinutes(totalSeconds),
            totalSeconds - TimeUnit.MINUTES.toSeconds(TimeUnit.SECONDS.toMinutes(totalSeconds))
        )
        showLogAssert("time", time)

        _getResponseTimer.postValue(Response.Time(time))

        totalSeconds -= 1

        if (totalSeconds == 0.toLong())
            _getResponseTimer.postValue(Response.Finish(true))

    }

    sealed class Response {
        data class Finish(val isFinish: Boolean) : Response()
        data class Time(val timer: String) : Response()
    }

}