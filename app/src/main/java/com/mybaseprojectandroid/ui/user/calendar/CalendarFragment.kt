package com.mybaseprojectandroid.ui.user.calendar

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentCalendarBinding
import java.time.LocalDate
import java.util.*

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    private lateinit var binding: FragmentCalendarBinding

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        binding.calendar.apply {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                selectCustomMultiple(LocalDate.of(2022, 6, 8), LocalDate.of(2022, 6, 14))
//                selectCustom(LocalDate.of(2022, 6, 12), LocalDate.of(2022, 6, 20), true)
            }
//            selectWeek(2022, 6, 0, true)
        }
    }


}