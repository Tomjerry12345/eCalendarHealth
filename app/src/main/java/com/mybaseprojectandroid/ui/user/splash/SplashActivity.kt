package com.mybaseprojectandroid.ui.user.splash

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.user.OnBoarding

class SplashActivity : AppCompatActivity() {
    var onBoardingScreeen: SharedPreferences? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
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
            val intent = Intent(this, OnBoarding::class.java)
            startActivity(intent)
            finish()
        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}