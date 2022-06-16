package com.mybaseprojectandroid.ui.main.calendar

import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentCalendarBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.getColor
import com.prolificinteractive.materialcalendarview.CalendarDay
import com.prolificinteractive.materialcalendarview.MaterialCalendarView

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    companion object {
        fun newInstance(): CalendarFragment {
            return CalendarFragment()
        }
    }

    private lateinit var binding: FragmentCalendarBinding

    private val viewModel: CalendarViewModel by viewModels {
        FactoryViewModel(CalendarViewModel(FirebaseDatabase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentCalendarBinding.bind(view)

        binding.calendar.selectionMode = MaterialCalendarView.SELECTION_MODE_NONE

        getData()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

//            val range = DateCustom.getRangeWeek()
//
//            var day = DateCustom.getDayByLocalDate(range.startDate)
//            val month = DateCustom.getMonthByLocalDate(range.startDate)
//            val year = DateCustom.getYearByLocalDate(range.startDate)
//
//            for (i in 1..7) {
//                showLogAssert("day", "$day")
//                binding.calendar.setDateSelected(CalendarDay.from(year, month, day), true)
//                day += 1
//            }
        }

//        binding.calendar.apply {
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//                selectCustomMultiple(LocalDate.of(2022, 6, 8), LocalDate.of(2022, 6, 14))
////                selectCustom(LocalDate.of(2022, 6, 12), LocalDate.of(2022, 6, 20), true)
//            }
////            selectWeek(2022, 6, 0, true)
//        }
    }

    private fun getData() {
        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val querySnapshot = it.data as QuerySnapshot

                    val data = querySnapshot.toObjects<Aktivitas>()

                    data.forEach { aktivitas ->
                        var day = aktivitas.startDate?.day
                        val month = aktivitas.startDate?.month
                        val year = aktivitas.startDate?.year

                        val sumWeekBring = aktivitas.sumWeekBring

                        showLogAssert("sumWeekBring", "$sumWeekBring")

                        if (sumWeekBring!! < 7) {
                            showLogAssert("sumWeekBring!! < 7", "true")
                            binding.calendar.selectionColor =
                                getColor(requireContext(), R.color.red)
                        } else {
                            showLogAssert("sumWeekBring!! >= 7", "true")
                            binding.calendar.selectionColor =
                                getColor(requireContext(), R.color.primary_color)
                        }

                        for (i in 1..7) {
                            binding.calendar.setDateSelected(
                                CalendarDay.from(
                                    year!!,
                                    month!!,
                                    day!!
                                ), true
                            )
                            day += 1
                        }
                    }

                }
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }


}