package com.mybaseprojectandroid.ui.main.calendar

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
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.vo.DateData

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
        getData()
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

                        val colorRed = getColor(requireContext(), R.color.red)
                        val colorGreen = getColor(requireContext(), R.color.primary_color)

                        if (sumWeekBring!! < 7) {
                            setDate(day!!, month!!, year!!, colorRed)
                        } else {
                            setDate(day!!, month!!, year!!, colorGreen)
                        }
                    }

                }
                is Response.Error -> TODO()
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }
    }

    private fun setDate(day: Int, month: Int, year: Int, color: Int) {
        var day1 = day
        for (i in 1..7) {
            binding.calendar.markDate(
                DateData(year, month, day1).setMarkStyle(
                    MarkStyle(MarkStyle.BACKGROUND, color)
                )
            )
            day1 += 1
        }
    }


}