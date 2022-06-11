package com.mybaseprojectandroid.ui.user.timer

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
import com.mybaseprojectandroid.ui.user.base.BaseActivity
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.Timer
import com.mybaseprojectandroid.utils.system.TimerCustom
import com.mybaseprojectandroid.utils.system.moveIntentTo


@RequiresApi(Build.VERSION_CODES.O)
class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var binding: FragmentTimerBinding

    private val viewModel: TimerViewModel by viewModels {
        FactoryViewModel(TimerViewModel(FirebaseDatabase()))
    }

    private var beforeDataAktivitas: Aktivitas? = null

    private val savedPasien = getSavedPasien()

    private val dayNow = TimerCustom.getDayNow()
    private val monthNow = TimerCustom.getMonthNow()
    private val yearNow = TimerCustom.getYearNow()

    private val hoursNow = TimerCustom.getHoursNow()

    private val weekOfMonth = TimerCustom.getWeeksMonth()


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

                    beforeDataAktivitas = dataExtract[0]

                    if (dayNow == beforeDataAktivitas?.dateUpdate?.day!!) {
                        if (beforeDataAktivitas?.dateUpdate?.hours!! >= hoursNow) {
                            beforeDataAktivitas!!.sumDayBring = 0
                        }
                    } else if (dayNow > beforeDataAktivitas?.dateUpdate?.day!!) {
                        showLogAssert("dayNow", "> hoursNow")
                        beforeDataAktivitas!!.sumDayBring = 0
                    }

                    if (beforeDataAktivitas?.sumDayBring!! >= 2 || beforeDataAktivitas?.sumWeekBring!! >= 5) {
                        binding.mulai.isEnabled = false
                    }

                    if (beforeDataAktivitas?.sumWeekBring!! >= 5 && weekOfMonth > beforeDataAktivitas!!.week!!) {
                        beforeDataAktivitas!!.isUpdate = false
                    }
//
//                    if (dayNow >= beforeDataAktivitas?.endDate?.day!!) {
//
//                        if (binding.mulai.isEnabled) {
//                            binding.mulai.isEnabled = false
//                        }
//
//                        beforeDataAktivitas?.statusUpdate = false
//                    }

                    showLogAssert("dataPasienModel", "$dataExtract")
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

                        if (beforeDataAktivitas!!.isUpdate == true) {
                            if (beforeDataAktivitas != null) {
                                beforeDataAktivitas!!.sumDayBring =
                                    beforeDataAktivitas!!.sumDayBring?.plus(
                                        1
                                    )
                                beforeDataAktivitas!!.sumWeekBring =
                                    beforeDataAktivitas!!.sumWeekBring?.plus(
                                        1
                                    )
                            } else {
                                addNewData()
                            }
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

    private fun addNewData() {
        beforeDataAktivitas = Aktivitas(
            idUser = savedPasien.id,
            sumDayBring = 1,
            sumWeekBring = 1,
            week = weekOfMonth,
            dateUpdate = DateModel(
                day = dayNow + 1,
                month = monthNow,
                year = yearNow,
                hours = hoursNow
            ),
            isUpdate = true
        )

        viewModel.addAktivitas(beforeDataAktivitas!!)
    }
}