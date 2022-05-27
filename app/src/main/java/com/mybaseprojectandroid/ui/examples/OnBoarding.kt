package com.mybaseprojectandroid.ui.examples

import android.content.Intent
import android.os.Bundle
import android.text.Html
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager.widget.ViewPager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.mybaseprojectandroid.R
import com.mybaseprojectandroid.ui.examples.auth.AutentikasiActivity
import com.mybaseprojectandroid.ui.examples.base.BaseActivity
import com.mybaseprojectandroid.utils.system.SliderAdapter


class OnBoarding : AppCompatActivity() {

    //Variables
    lateinit var viewPager: ViewPager
    var dotsLayout: LinearLayout? = null
    var sliderAdapter: SliderAdapter? = null
    var dots: Array<TextView?>? = null
    var letsGetStarted: TextView? = null
    var animation: Animation? = null
    var currentPos = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_on_boarding)


        //Hooks
        viewPager = findViewById(R.id.slider);
        dotsLayout = findViewById(R.id.dots);
        letsGetStarted = findViewById(R.id.get_started_btn)

        //Call adapter
        sliderAdapter =  SliderAdapter(this)
        viewPager.setAdapter(sliderAdapter)

        //Dots
        addDots(0);
        viewPager.addOnPageChangeListener(changeListener);
    }

    fun skip(view: View?) {
        startActivity(Intent(this, AutentikasiActivity::class.java))
        finish()
    }

    fun next(view: View?) {
        viewPager.currentItem = currentPos + 1
    }

    private fun addDots(position: Int) {
        dots = arrayOfNulls(4)
        dotsLayout!!.removeAllViews()
        for (i in 0 until dots!!.size) {
            dots!![i] = TextView(this)
            dots!![i]?.text = Html.fromHtml("&#8226;")
            dots!![i]?.setTextSize(35F)
            dotsLayout!!.addView(dots!![i])
        }
        if (dots!!.size > 0) {
            dots!![position]?.setTextColor(resources.getColor(R.color.primary_color))
        }
    }

    var changeListener: OnPageChangeListener = object : OnPageChangeListener {
        override fun onPageScrolled(
            position: Int,
            positionOffset: Float,
            positionOffsetPixels: Int
        ) {
        }

        override fun onPageSelected(position: Int) {
            addDots(position)
            currentPos = position
            if (position == 0) {
                letsGetStarted!!.visibility = View.GONE
            } else if (position == 1) {
                letsGetStarted!!.visibility = View.GONE
            } else if (position == 2) {
                letsGetStarted!!.visibility = View.GONE
            } else {
                animation = AnimationUtils.loadAnimation(this@OnBoarding, R.anim.bottom_anim)
                letsGetStarted!!.animation = animation
                letsGetStarted!!.visibility = View.VISIBLE
            }
        }

        override fun onPageScrollStateChanged(state: Int) {}
    }

    fun start(view: View) {
        startActivity(Intent(this, AutentikasiActivity::class.java))
        finish()
    }

}