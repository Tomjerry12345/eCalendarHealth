package com.mybaseprojectandroid.utils.system

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mybaseprojectandroid.ui.user.calendar.CalendarFragment
import com.mybaseprojectandroid.ui.user.history.HistoryFragment
import com.mybaseprojectandroid.ui.user.home.HomeFragment
import com.mybaseprojectandroid.ui.user.profile.ProfileFragment

@Suppress("DEPRECATION")
class MainPagerAdapter(var fm : FragmentManager) : FragmentStatePagerAdapter(fm){

    override fun getItem(position: Int): Fragment {
        return when(position){
            0 -> HomeFragment.newInstance()
            1 -> HistoryFragment.newInstance()
            2 -> CalendarFragment.newInstance()
            3 -> ProfileFragment.newInstance()
            else -> HomeFragment.newInstance()
        }
    }

    override fun getCount(): Int {
        return 4
    }

}