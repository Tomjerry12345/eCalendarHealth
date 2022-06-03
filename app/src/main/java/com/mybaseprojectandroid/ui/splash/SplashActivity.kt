package com.mybaseprojectandroid.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.onBoarding.OnBoarding
import com.mybaseprojectandroid.ui.user.base.BaseActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveIntentTo

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SavedData.init(this)
        Handler(Looper.getMainLooper()).postDelayed({
//            SavedData.setBoolean(Constant.KEY_IS_LOGGIN, false)
            val isLoggin = SavedData.getBoolean(Constant.KEY_IS_LOGGIN)

            showLogAssert("isLoggin", "$isLoggin")

            if (isLoggin) {
                moveIntentTo(this, BaseActivity(), true)
            } else {
                moveIntentTo(this, OnBoarding(), true)
            }
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}