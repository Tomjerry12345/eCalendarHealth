package com.mybaseprojectandroid.utils.system

import android.os.Build
import androidx.annotation.RequiresApi
import com.mybaseprojectandroid.model.RangeWeek
import java.sql.Timestamp
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.time.temporal.*
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
object DateCustom {

    private val current: LocalDateTime = LocalDateTime.now()

    private val now = LocalDate.now()

    val listNameMonth = listOf(
        "Januari",
        "Februari",
        "Maret",
        "April",
        "Mei",
        "Juni",
        "Juli",
        "Agustus",
        "September",
        "Oktober",
        "November",
        "Desember",
    )

    fun getDayNow(): Int {
        return current.get(ChronoField.DAY_OF_MONTH)
    }

    fun getMonthNow(): Int {
        return current.get(ChronoField.MONTH_OF_YEAR)
    }

    fun getYearNow(): Int {
        return current.get(ChronoField.YEAR)
    }

    fun getDayByLocalDate(date: LocalDate): Int {
        return date.get(ChronoField.DAY_OF_MONTH)
    }

    fun getMonthByLocalDate(date: LocalDate): Int {
        return date.get(ChronoField.MONTH_OF_YEAR)
    }

    fun getYearByLocalDate(date: LocalDate): Int {
        return date.get(ChronoField.YEAR)
    }

    fun getHoursNow(): Int {
        return current.get(ChronoField.HOUR_OF_AMPM)
    }

    fun getMinuteNow(): Int {
        return current.get(ChronoField.MINUTE_OF_HOUR)
    }

    fun getSecondNow(): Int {
        return current.get(ChronoField.SECOND_OF_MINUTE)
    }

    fun getWeeksMonth(): Int {
        val weekFields = WeekFields.of(Locale.getDefault())
        return current.get(weekFields.weekOfMonth())
    }

    fun getTimestamp(): Long {
        val timestamp = Timestamp(System.currentTimeMillis())
        return timestamp.time
    }

    fun getNameMonth(month: Int): String {
        return listNameMonth[month]
    }

    fun getRangeWeek(): RangeWeek {
        val firstDayOfWeek = WeekFields.of(Locale.getDefault()).firstDayOfWeek
        val lastDayOfWeek =  DayOfWeek.of(((firstDayOfWeek.value + 5) % DayOfWeek.values().size) + 1)
        val startDay: LocalDate = now.with(TemporalAdjusters.previousOrSame(firstDayOfWeek))
        val lastDay: LocalDate = now.with(TemporalAdjusters.nextOrSame(lastDayOfWeek))

        return RangeWeek(startDay, lastDay)
    }

    fun getTimeNotif(): String {
        val sdf = DateTimeFormatter.ofPattern("hh:mm:a")
        return current.format(sdf)
    }
}