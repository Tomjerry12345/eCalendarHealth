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
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.getColor
import sun.bob.mcalendarview.MarkStyle
import sun.bob.mcalendarview.vo.DateData

class CalendarFragment : Fragment(R.layout.fragment_calendar) {

    companion object {
        fun newInstance() = CalendarFragment()
    }

    private lateinit var binding: FragmentCalendarBinding

    private val viewModel: CalendarViewModel by viewModels {
        FactoryViewModel(CalendarViewModel(FirebaseDatabase()))
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCalendarBinding.bind(view)
        binding.viewModel = viewModel
        showLogAssert("pasien", "${getSavedPasien()}")
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