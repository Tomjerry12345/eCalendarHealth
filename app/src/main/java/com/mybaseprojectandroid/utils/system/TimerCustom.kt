package com.mybaseprojectandroid.utils.system

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.temporal.ChronoField
import java.time.temporal.WeekFields
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object TimerCustom {

    private val current: LocalDateTime = LocalDateTime.now()

    fun getDayNow(): Int {
        return current.get(ChronoField.DAY_OF_MONTH)
    }

    fun getMonthNow(): Int {
        return current.get(ChronoField.MONTH_OF_YEAR)
    }

    fun getYearNow(): Int {
        return current.get(ChronoField.YEAR)
    }

    fun getHoursNow(): Int {
        return current.get(ChronoField.HOUR_OF_AMPM)
    }

    fun minuteNow(): Int {
        return current.get(ChronoField.MINUTE_OF_HOUR)
    }

    fun getWeeksMonth(): Int {
        val weekFields = WeekFields.of(Locale.getDefault())
        return current.get(weekFields.weekOfMonth())
    }
}