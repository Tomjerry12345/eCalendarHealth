package com.mybaseprojectandroid.ui.main.timer

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
import com.mybaseprojectandroid.model.DateModel
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
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


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTimerBinding.bind(view)

        binding.mulai.setOnClickListener {
            Timer.startTimer()
        }

        viewModel.data.observe(viewLifecycleOwner) {
            when (it) {
                is Response.Changed -> {
                    val data = it.data as QuerySnapshot

                    val dataExtract = data.toObjects<Aktivitas>()

                    if (dataExtract.isNotEmpty()) {
                        beforeDataAktivitas = dataExtract[0]

                        if (dayNow == beforeDataAktivitas?.dateUpdate?.day!!) {
                            if (beforeDataAktivitas?.dateUpdate?.hours!! >= hoursNow) {
                                beforeDataAktivitas!!.sumDayBring = 0
                            }
                        } else if (dayNow > beforeDataAktivitas?.dateUpdate?.day!!) {
                            beforeDataAktivitas!!.sumDayBring = 0
                        }

                        if (beforeDataAktivitas?.sumDayBring!! >= 2 || beforeDataAktivitas?.sumWeekBring!! >= 5) {
                            binding.mulai.apply {
                                isEnabled = false
                            }

                            binding.bgGreen.background =
                                getDrawable(requireContext(), R.drawable.bg_circular_error)

                            binding.timer.apply {
                                setTextColor(getColor(requireContext(), R.color.red))
                                text = "Limit"
                            }
                        }

                        if (weekOfMonth > beforeDataAktivitas!!.week!!) {
                            beforeDataAktivitas!!.isUpdate = false
                        }
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

                        if (beforeDataAktivitas != null) {
                            if (beforeDataAktivitas!!.isUpdate == true) {
                                beforeDataAktivitas!!.sumDayBring =
                                    beforeDataAktivitas!!.sumDayBring?.plus(
                                        1
                                    )
                                beforeDataAktivitas!!.sumWeekBring =
                                    beforeDataAktivitas!!.sumWeekBring?.plus(
                                        1
                                    )
                                addNewData()
                            } else {
                                initData()
                                addNewData()
                            }

                        } else {
                            initData()
                            addNewData()
                        }

                        showLogAssert("aktivitas", "$beforeDataAktivitas")


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

    private fun initData() {
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
            month = monthNow,
            isUpdate = true
        )
    }

    private fun addNewData() {
        viewModel.addAktivitas(beforeDataAktivitas!!)
    }
}