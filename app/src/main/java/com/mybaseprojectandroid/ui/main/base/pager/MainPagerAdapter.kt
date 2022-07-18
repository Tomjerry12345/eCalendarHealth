package com.mybaseprojectandroid.ui.main.base.pager

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import com.mybaseprojectandroid.ui.main.calendar.CalendarFragment
import com.mybaseprojectandroid.ui.main.history.HistoryFragment
import com.mybaseprojectandroid.ui.main.home.pasien.HomePasienFragment
import com.mybaseprojectandroid.ui.main.home.perawat.HomePerawatFragment
import com.mybaseprojectandroid.ui.main.profile.ProfileFragment
import com.mybaseprojectandroid.utils.local.getSavedAdmin
import com.mybaseprojectandroid.utils.local.getSavedPasien

@Suppress("DEPRECATION")
class MainPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    @RequiresApi(Build.VERSION_CODES.O)
    override fun getItem(position: Int): Fragment {
        return when (position) {
            0 -> {
                if (getSavedPasien()?.typeAkun == "perawat") {
                    HomePerawatFragment.newInstance()
                } else {
                    HomePasienFragment.newInstance()
                }
            }
            1 -> HistoryFragment.newInstance()
            2 -> CalendarFragment.newInstance()
            3 -> ProfileFragment.newInstance()
            else -> {
                if (getSavedPasien()?.typeAkun == "perawat") {
                    HomePerawatFragment.newInstance()
                } else {
                    HomePasienFragment.newInstance()
                }
            }
        }
    }

    override fun getCount(): Int {
        return 4
    }

}