package com.mybaseprojectandroid.ui.main.calendar.adapter

import android.view.View
import android.widget.TextView
import com.kizitonwose.calendar.view.ViewContainer
import com.mybaseprojectandroid.R

class DayViewContainer(view: View) : ViewContainer(view) {
    val textView = view.findViewById<TextView>(R.id.calendarDayText)


    // With ViewBinding
    // val textView = CalendarDayLayoutBinding.bind(view).calendarDayText
}