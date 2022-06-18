package com.mybaseprojectandroid.ui.main.aktivitas

import android.media.MediaPlayer.OnCompletionListener
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.MediaController
import android.widget.Toast
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
        video()
        back()


    }

    private fun video() {
        val videoPath = "android.resource://" + context?.packageName.toString() + "/" + R.raw.briskwalking
        val uri: Uri = Uri.parse(videoPath)
//        binding.videoView.setVideoURI(uri)
//        val mediaController = MediaController(context)
//        binding.videoView.setMediaController(mediaController)
//        mediaController.setAnchorView(binding.videoView)

        val mediaController = MediaController(context)
        mediaController.setAnchorView(binding.videoView)
        mediaController.setMediaPlayer(binding.videoView)


// video view
        binding.videoView.setVideoURI(uri)
        binding.videoView.setMediaController(mediaController)
        binding.videoView.setOnCompletionListener(OnCompletionListener {
            binding.masuk.isEnabled = true
        })

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