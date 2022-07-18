package com.mybaseprojectandroid.ui.main.base

import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.viewpager.widget.ViewPager
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.databinding.FragmentBaseBinding
import com.mybaseprojectandroid.ui.main.base.pager.MainPagerAdapter
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.local.getSavedAdmin
import com.mybaseprojectandroid.utils.local.getSavedPasien
import com.mybaseprojectandroid.utils.system.moveIntentTo


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
        bottomNavBar.menu.getItem(SavedData.getInt("position")).isChecked = true
        viewPagerr.currentItem = SavedData.getInt("position")
//        viewPagerr = binding.fragmentContainer
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
        if (getSavedPasien()?.typeAkun == "perawat") {
            bottomNavBar.menu.findItem(R.id.menu_history).isEnabled = false
            bottomNavBar.menu.findItem(R.id.menu_calendar).isEnabled = false
//            bottomNavBar.menu.findItem(R.id.menu_profile).isEnabled = false
        }
        bottomNavBar.setOnNavigationItemSelectedListener {
            when (it.itemId) {
//                R.id.menu_home -> viewPagerr.currentItem = 0
//                R.id.menu_calendar -> viewPagerr.currentItem = 1
//                R.id.menu_history ->
//                    viewPagerr.currentItem = 2
//                R.id.menu_profile -> viewPagerr.currentItem = 3
                R.id.menu_home -> {
                    SavedData.setInt("position", 0)
                }
                R.id.menu_history -> {
                    SavedData.setInt("position", 1)

                }
                R.id.menu_calendar -> {
                    SavedData.setInt("position", 2)
                }
                R.id.menu_profile -> {
                    SavedData.setInt("position", 3)
                }
            }
            moveIntentTo(requireActivity(), BaseActivity(), true)
            return@setOnNavigationItemSelectedListener true
        }
    }

    private fun setViewPagerAdapter() {
        viewPagerr.adapter = MainPagerAdapter(childFragmentManager)

    }
}