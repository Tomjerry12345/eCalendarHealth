package com.mybaseprojectandroid.ui.main.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.utils.other.showLogAssert

class BaseActivity : AppCompatActivity() {

    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController

//        getNotifData()

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }

    fun getNotifData() {
        val extras = intent.extras
        val clicked = extras?.getString("clicked")
        showLogAssert("clicked", "$clicked")
    }
}