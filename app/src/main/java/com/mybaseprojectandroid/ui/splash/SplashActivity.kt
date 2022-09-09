package com.mybaseprojectandroid.ui.splash

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.firestore.ktx.toObjects
import com.mybaseprojectandroid.BuildConfig
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.database.firebase.FirebaseDatabase
import com.mybaseprojectandroid.model.AppModel
import com.mybaseprojectandroid.ui.main.base.BaseActivity
import com.mybaseprojectandroid.ui.maintenance.MaintenanceActivity
import com.mybaseprojectandroid.ui.onBoarding.OnBoarding
import com.mybaseprojectandroid.utils.local.SavedData
import com.mybaseprojectandroid.utils.local.setSavedAdmin
import com.mybaseprojectandroid.utils.local.setSavedPasien
import com.mybaseprojectandroid.utils.network.Response
import com.mybaseprojectandroid.utils.other.Constant
import com.mybaseprojectandroid.utils.other.FactoryViewModel
import com.mybaseprojectandroid.utils.other.showLogAssert
import com.mybaseprojectandroid.utils.system.moveIntentTo

@SuppressLint("CustomSplashScreen")
class SplashActivity : AppCompatActivity() {

    private val viewModel: SplashViewModel by viewModels {
        FactoryViewModel(SplashViewModel(FirebaseDatabase()))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        SavedData.init(this)
        SavedData.setInt("position", 0)

        Handler(Looper.getMainLooper()).postDelayed({
            viewModel.app.observe(this) {
                when(it) {
                    is Response.Changed -> {
                        val app = it.data as QuerySnapshot
                        val dataExtract = app.toObjects<AppModel>()
                        showLogAssert("app", "$dataExtract")
                        val versionName: String = BuildConfig.VERSION_NAME
                        if (dataExtract[0].version == versionName) {
                            val isLoggin = SavedData.getBoolean(Constant.KEY_IS_LOGGIN)
                            if (isLoggin) {
                                moveIntentTo(this, BaseActivity(), true)
                            } else {
                                setSavedAdmin(null)
                                setSavedPasien(null)
                                moveIntentTo(this, OnBoarding(), true)
                            }
                        } else {
                            val intent = Intent(this, MaintenanceActivity::class.java)

                            if (dataExtract[0].maintanance == true) {
                                intent.putExtra("isVersion", "false")
                                intent.putExtra("message", dataExtract[0].message!!)

                            } else {
                                intent.putExtra("isVersion", "true")
                                intent.putExtra("message", dataExtract[0].message!!)
                            }
                            startActivity(intent)
                            finish()
                        }
//
                    }
                    is Response.Error -> {
                        showLogAssert("error", it.error)
                    }
                    is Response.Progress -> TODO()
                    is Response.Success -> TODO()
                }
            }

        }, 3000) // 3000 is the delayed time in milliseconds.
    }
}