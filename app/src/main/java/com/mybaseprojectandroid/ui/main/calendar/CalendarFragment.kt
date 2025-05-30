package com.mybaseprojectandroid.ui.main.calendar

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.kizitonwose.calendar.core.CalendarDay
import com.kizitonwose.calendar.core.DayPosition
import com.kizitonwose.calendar.core.firstDayOfWeekFromLocale
import com.kizitonwose.calendar.view.MonthDayBinder
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentCalendarBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.ui.main.calendar.adapter.DayViewContainer
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.getColor
import java.time.LocalDate
import java.time.YearMonth

//import sun.bob.mcalendarview.MarkStyle
//import sun.bob.mcalendarview.vo.DateData

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    private lateinit var binding: FragmentCalendarBinding

    private val viewModel: CalendarViewModel by viewModels {
        FactoryViewModel(CalendarViewModel(FirebaseDatabase()))
    }

    private val markedDates: MutableMap<LocalDate, Int> = mutableMapOf()

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        binding.viewModel = viewModel

        setupCalendar()

        getData()



    }

    override fun onDestroy() {
        super.onDestroy()
//        binding.calendar.markedDates.all.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setupCalendar() {
        binding.calendar.dayBinder = object : MonthDayBinder<DayViewContainer> {
            override fun create(view: View): DayViewContainer = DayViewContainer(view)

            @SuppressLint("ResourceType")
            @RequiresApi(Build.VERSION_CODES.O)
            override fun bind(container: DayViewContainer, data: CalendarDay) {
                val textView = container.textView
                textView.text = data.date.dayOfMonth.toString()
                if (data.position == DayPosition.MonthDate) {
                    textView.visibility = View.VISIBLE
                    if (markedDates.containsKey(data.date)) {
                        textView.setTextColor(Color.WHITE)
                        textView.setBackgroundColor(markedDates[data.date] ?: Color.GREEN)
                    } else {
                        textView.setTextColor(Color.BLACK)
                        textView.background = null
                    }
                } else {
                    textView.visibility = View.INVISIBLE
                }
            }
        }

        val currentMonth = YearMonth.now()
        val startMonth = currentMonth.minusMonths(100)
        val endMonth = currentMonth.plusMonths(100)
        val firstDayOfWeek = firstDayOfWeekFromLocale()

        binding.calendar.setup(startMonth, endMonth, firstDayOfWeek)
        binding.calendar.scrollToMonth(currentMonth)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData() {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val data = querySnapshot.toObjects<Aktivitas>()

                    data.forEach { aktivitas ->
                        val day = aktivitas.startDate?.day
                        val month = aktivitas.startDate?.month
                        val year = aktivitas.startDate?.year

                        val sumWeekBring = aktivitas.sumWeekBring

                        showLogAssert("day", day.toString())
                        showLogAssert("sumWeekBring", sumWeekBring.toString())

                        val color1 = getColor(requireContext(), R.color.color1)
                        val color2 = getColor(requireContext(), R.color.color2)
                        val color3 = getColor(requireContext(), R.color.color3)
                        val color4 = getColor(requireContext(), R.color.color4)
                        val color5 = getColor(requireContext(), R.color.color5)

                        when(sumWeekBring) {
                            1 -> setDate(day!!, month!!, year!!, color1)
                            2 -> setDate(day!!, month!!, year!!, color2)
                            3 -> setDate(day!!, month!!, year!!, color3)
                            4 -> setDate(day!!, month!!, year!!, color4)
                            5 -> setDate(day!!, month!!, year!!, color5)
                        }
                    }

                }
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

//    private fun setDate(day: Int, month: Int, year: Int, color: Int) {
//        var day1 = day
//        for (i in 1..7) {
////            binding.calendar.markDate(
////                DateData(year, month, day1).setMarkStyle(
////                    MarkStyle(MarkStyle.BACKGROUND, color)
////                )
////            )
//            day1 += 1
//        }
//
//    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun setDate(day: Int, month: Int, year: Int, color: Int) {
        var day1 = day
        for (i in 1..7) {
            val date = LocalDate.of(year, month, day1)
            markedDates[date] = color
            day1 += 1
        }

        showLogAssert("markedDates", markedDates)

        binding.calendar.notifyCalendarChanged()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun testCalendar() {
//        val markedDates: List<LocalDate> = listOf(
//            LocalDate.of(2025, 5, 1),
//            LocalDate.of(2025, 5, 10),
//            LocalDate.of(2025, 5, 30)
//        )

    }
}