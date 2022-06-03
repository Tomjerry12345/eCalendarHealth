package com.mybaseprojectandroid.ui.user.aktivitas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentAktivitasBinding
import com.mybaseprojectandroid.utils.system.moveNavigationTo


class AktivitasFragment : Fragment(R.layout.fragment_aktivitas) {

    private lateinit var binding: FragmentAktivitasBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentAktivitasBinding.bind(view)

        binding.masuk.setOnClickListener {
            moveNavigationTo(requireView(), R.id.action_aktivitasFragment_to_timerFragment)
        }

    }

}