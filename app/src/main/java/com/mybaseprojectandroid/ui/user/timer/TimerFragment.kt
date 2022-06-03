package com.mybaseprojectandroid.ui.user.timer

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentTimerBinding
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.Timer


class TimerFragment : Fragment(R.layout.fragment_timer) {

    private lateinit var binding: FragmentTimerBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentTimerBinding.bind(view)

        binding.mulai.setOnClickListener {
            Timer.startTimer()
        }

        Timer.getResponseTimer.observe(viewLifecycleOwner) {
            when (it) {
                is Timer.Response.Finish -> {
                    if (it.isFinish) {
                        Timer.cancelTimer()
                    }
                    showLogAssert("finish", "${it.isFinish}")
                }
                is Timer.Response.Time -> {
                    showLogAssert("getTimer", it.timer)
                    binding.timer.text = it.timer
                }
            }
        }
    }
}