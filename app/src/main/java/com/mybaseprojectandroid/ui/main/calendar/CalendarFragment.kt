package com.mybaseprojectandroid.ui.main.calendar

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentCalendarBinding
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.DateCustom
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    private lateinit var binding: FragmentCalendarBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        binding.calendar.selectionMode = MaterialCalendarView.SELECTION_MODE_NONE

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val range = DateCustom.getRangeWeek()

            var day = DateCustom.getDayByLocalDate(range.startDate)
            val month = DateCustom.getMonthByLocalDate(range.startDate)
            val year = DateCustom.getYearByLocalDate(range.startDate)

            for (i in 1..7) {
                showLogAssert("day", "$day")
                binding.calendar.setDateSelected(CalendarDay.from(year, month, day), true)
                day += 1
            }
        }

//        binding.calendar.apply {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                selectCustomMultiple(LocalDate.of(2022, 6, 8), LocalDate.of(2022, 6, 14))
////                selectCustom(LocalDate.of(2022, 6, 12), LocalDate.of(2022, 6, 20), true)
//            }
////            selectWeek(2022, 6, 0, true)
//        }
    }


}