package com.mybaseprojectandroid.ui.main.timer

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.databinding.FragmentTimerBinding
import com.mybaseprojectandroid.model.Aktivitas
import com.mybaseprojectandroid.model.DateBringWalking
import com.mybaseprojectandroid.model.DateModel
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.other.showToast
import com.mybaseprojectandroid.utils.system.*


@RequiresApi(Build.VERSION_CODES.O)
class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var binding: FragmentTimerBinding

    private val viewModel: TimerViewModel by viewModels {
        FactoryViewModel(TimerViewModel(FirebaseDatabase()))
    }

    private var beforeDataAktivitas: Aktivitas? = null

    private val savedPasien = getSavedPasien()

    private val dayNow = DateCustom.getDayNow()
    private val monthNow = DateCustom.getMonthNow()
    private val yearNow = DateCustom.getYearNow()
    private val hoursNow = DateCustom.getHoursNow()
    private val weekOfMonth = DateCustom.getWeeksMonth()


    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val range = DateCustom.getRangeWeek()

        val dayStart = DateCustom.getDayByLocalDate(range.startDate)
        val monthStart = DateCustom.getMonthByLocalDate(range.startDate)
        val yearStart = DateCustom.getYearByLocalDate(range.startDate)

        val dateStart = DateModel(
            dayStart,
            monthStart,
            yearStart,
            hoursNow
        )

        val dayEnd = DateCustom.getDayByLocalDate(range.endDate)
        val monthEnd = DateCustom.getMonthByLocalDate(range.endDate)
        val yearEnd = DateCustom.getYearByLocalDate(range.endDate)

        val dateEnd = DateModel(
            dayEnd,
            monthEnd,
            yearEnd,
            hoursNow
        )

        binding = FragmentTimerBinding.bind(view)

        binding.mulai.setOnClickListener {
            if (binding.timer.text == "MULAI") {
                Timer.startTimer()
            } else {
                addDateBringWalking(dateStart)

                if (beforeDataAktivitas != null) {
                    if (beforeDataAktivitas!!.isUpdate == true) {

                        beforeDataAktivitas!!.sumDayBring =
                            beforeDataAktivitas!!.sumDayBring?.plus(1)

                        beforeDataAktivitas!!.sumWeekBring =
                            beforeDataAktivitas!!.sumWeekBring?.plus(1)

                        showLogAssert(
                            "test",
                            "${beforeDataAktivitas!!.sumDayBring}"
                        )
                        addNewData()
                    } else {
                        initData(dateStart, dateEnd)
                        addNewData()
                    }

                } else {
                    initData(dateStart, dateEnd)
                    addNewData()
                }
            }
        }



        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val data = it.data as QuerySnapshot

                    val dataExtract = data.toObjects<Aktivitas>()

                    if (dataExtract.isNotEmpty()) {
                        beforeDataAktivitas = dataExtract[0]

//                        if (dayNow == beforeDataAktivitas?.dateUpdate?.day!!) {
//                            if (beforeDataAktivitas?.dateUpdate?.hours!! >= hoursNow) {
//                                beforeDataAktivitas!!.sumDayBring = 0
//                            }
//                        } else if (dayNow > beforeDataAktivitas?.dateUpdate?.day!!) {
//                            beforeDataAktivitas!!.sumDayBring = 0
//                        }
//
//                        if (beforeDataAktivitas?.sumDayBring!! >= 2 || beforeDataAktivitas?.sumWeekBring!! >= 5) {
//                            binding.mulai.setOnClickListener {
//                                moveIntentTo(requireActivity(), BaseActivity(), true)
//                            }
//
//                            binding.bgGreen.background =
//                                getDrawable(requireContext(), R.drawable.bg_circular_error)
//
//                            binding.timer.apply {
//                                setTextColor(getColor(requireContext(), R.color.red))
//                                text = "Selesai"
//                            }
//                        }
//
//                        if (weekOfMonth > beforeDataAktivitas!!.week!!) {
//                            beforeDataAktivitas!!.isUpdate = false
//                        }
                    }


                }
                is Response.Error -> {
                    showLogAssert("error get aktivitas", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> TODO()
            }
        }

        Timer.getResponseTimer.observe(viewLifecycleOwner) {
            when (it) {
                is Timer.Response.Finish -> {
                    if (it.isFinish) {
                        Timer.cancelTimer()

                        binding.bgGreen.background =
                            getDrawable(requireContext(), R.drawable.bg_circular_error)

                        binding.timer.apply {
                            setTextColor(getColor(requireContext(), R.color.red))
                            text = "Selesai"
                        }

//                        binding.mulai.setOnClickListener {
//                            if (binding.timer.text == "Selesai") {
//
//                            } else {
//                                showToast(requireContext(), "terjadi kesalahan logic selesai")
//                            }
//                        }


                    }
                }
                is Timer.Response.Time -> {
                    binding.timer.text = it.timer
                }
            }
        }

        viewModel.response.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> TODO()
                is Response.Error -> {
                    showLogAssert("error add aktivitas", it.error)
                }
                is Response.Progress -> TODO()
                is Response.Success -> {
                    moveIntentTo(requireActivity(), BaseActivity(), true)
                }
            }
        }
    }

    private fun initData(dateStart: DateModel, dateEnd: DateModel) {
        beforeDataAktivitas = Aktivitas(
            idUser = savedPasien?.id,
            sumDayBring = 1,
            sumWeekBring = 1,
            week = weekOfMonth,
            dateUpdate = DateModel(
                day = dayNow + 1,
                month = monthNow,
                year = yearNow,
                hours = hoursNow
            ),
            startDate = dateStart,
            endDate = dateEnd,
            month = monthNow,
            isUpdate = true,
            timeStamp = DateCustom.getTimestamp()
        )
    }

    private fun addNewData() {
        viewModel.addAktivitas(beforeDataAktivitas!!)
    }

    private fun addDateBringWalking(date: DateModel) {

        val dateBringWalking = DateBringWalking(
            idUser = savedPasien?.id,
            date = date,
            week = weekOfMonth,
            month = monthNow,
            timeStamp = DateCustom.getTimestamp()
        )

        viewModel.addDateBringWalking(dateBringWalking)
    }
}