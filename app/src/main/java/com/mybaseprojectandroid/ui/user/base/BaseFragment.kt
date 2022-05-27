package com.mybaseprojectandroid.ui.user.base

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.OnBackPressedCallback
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentBaseBinding
import com.mybaseprojectandroid.utils.system.MainPagerAdapter


class BaseFragment : Fragment(R.layout.fragment_base) {
    private lateinit var viewPagerr: ViewPager
    var exit = false

    private lateinit var bottomNavBar: BottomNavigationView
    private lateinit var binding: FragmentBaseBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        onBackPressed()

        binding = FragmentBaseBinding.bind(view)
        viewPagerr = binding.fragmentContainer

        bottomNavBar = binding.bottomNavigationView

        setViewPagerAdapter()
        setBottomNavigation()
        viewPagerr = binding.fragmentContainer
    }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (exit) {
                        activity?.finish()
                        return
                    } else {
                        exit = true
                        Handler().postDelayed({ exit = false }, 2000)
                    }
                }
            })
    }

    private fun setBottomNavigation() {
        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.menu_home -> viewPagerr.currentItem = 0
                R.id.menu_history -> viewPagerr.currentItem = 1
                R.id.menu_calendar -> viewPagerr.currentItem = 2
                R.id.menu_profile -> viewPagerr.currentItem = 3
            }
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerAdapter() {
        viewPagerr.adapter = MainPagerAdapter(childFragmentManager)

    }
}