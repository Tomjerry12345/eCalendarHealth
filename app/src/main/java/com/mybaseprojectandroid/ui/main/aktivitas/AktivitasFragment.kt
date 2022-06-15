package com.mybaseprojectandroid.ui.main.aktivitas

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentAktivitasBinding
import com.mybaseprojectandroid.utils.system.moveNavigationTo
import com.mybaseprojectandroid.utils.system.popNavigation


class AktivitasFragment : Fragment(R.layout.fragment_aktivitas) {

    private lateinit var binding: FragmentAktivitasBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAktivitasBinding.bind(view)

        masuk()
        back()

    }

    private fun masuk() {
        binding.masuk.setOnClickListener {
            moveNavigationTo(requireView(), R.id.action_aktivitasFragment_to_timerFragment)
        }
    }

    private fun back() {
        binding.back.setOnClickListener {
            popNavigation(requireView())
        }
    }

}