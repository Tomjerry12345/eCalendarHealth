package com.mybaseprojectandroid.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.onBoarding.OnBoarding
import com.mybaseprojectandroid.ui.user.base.BaseActivity
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.system.moveIntentTo

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SavedData.init(this)
        // This is used to hide the status bar and make
        // the splash screen as a full screen activity.


        // we used the postDelayed(Runnable, time) method
        // to send a message with a delayed time.
        Handler().postDelayed({
//            onBoardingScreeen =getSharedPreferences("onBoardingScreen", MODE_PRIVATE)
//            val isFirstTime: Boolean = onBoardingScreeen!!.getBoolean("firstTime", true)
//            if (isFirstTime) {
//                val editor: SharedPreferences.Editor = onBoardingScreeen!!.edit()
//                editor.putBoolean("firstTime", false)
//                editor.commit()
//                val intent = Intent(getApplicationContext(), OnBoarding::class.java)
//                startActivity(intent)
//                finish()
//            } else {
//                val intent = Intent(getApplicationContext(), AutentikasiActivity::class.java)
//                startActivity(intent)
//                finish()
//            }
            val isLoggin = SavedData.getBoolean(Constant.KEY_IS_LOGGIN)

            if (isLoggin) {
                moveIntentTo(this, BaseActivity(), true)
            } else {
                moveIntentTo(this, OnBoarding(), true)
            }
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}