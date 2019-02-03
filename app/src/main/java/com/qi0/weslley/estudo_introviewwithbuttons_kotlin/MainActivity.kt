package com.qi0.weslley.estudo_introviewwithbuttons_kotlin

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem

import android.view.WindowManager
import android.os.Build
import android.os.VibrationEffect
import android.view.View
import android.text.Html
import android.widget.TextView
import android.support.v4.view.ViewPager
import kotlinx.android.synthetic.main.content_main.*
import android.os.Vibrator
import android.support.v4.content.ContextCompat
import android.widget.LinearLayout
import android.widget.Toast
import com.daimajia.androidanimations.library.Techniques
import com.daimajia.androidanimations.library.YoYo
import kotlinx.android.synthetic.main.activity_main.*
import android.text.Spanned






class MainActivity : AppCompatActivity() {

    private lateinit var mDots: Array<TextView?>
    private lateinit var mDotsLayout: LinearLayout
    private lateinit var mSliderViewPager: ViewPager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        transparentStatusAndNavigation()

        val sliderAdapter = SliderAdapter(this)
        mDotsLayout = dotsLayout

        mSliderViewPager = sliderViewPager
        mSliderViewPager.adapter = sliderAdapter
        mSliderViewPager.addOnPageChangeListener(viewListener)

        addDotsIndicator(0)

        btLogin.setOnClickListener {
            Toast.makeText(this, "Login", Toast.LENGTH_SHORT).show()
        }
        btRegister.setOnClickListener {
            Toast.makeText(this, "Register", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> true
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun transparentStatusAndNavigation() {
        //make full transparent statusBar
        if (Build.VERSION.SDK_INT in 19..20) {
            setWindowFlag(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, true)
        }
        if (Build.VERSION.SDK_INT >= 19) {
            window.decorView.systemUiVisibility =
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        }
        if (Build.VERSION.SDK_INT >= 21) {
            setWindowFlag(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                false
            )
            window.statusBarColor = Color.TRANSPARENT
        }
    }

    private fun setWindowFlag(bits: Int, on: Boolean) {
        val win = window
        val winParams = win.attributes
        if (on) {
            winParams.flags = winParams.flags or bits
        } else {
            winParams.flags = winParams.flags and bits.inv()
        }
        win.attributes = winParams
    }

    fun addDotsIndicator(position: Int) {

        mDots = arrayOfNulls(3)
        mDotsLayout.removeAllViews()

        for (i in 0 until mDots.size) {

            mDots[i] = TextView(this)
            //mDots[i]!!.text = Html.fromHtml("&#8226;")
            mDots[i]!!.text = fromHtml("&#8226;")
            mDots[i]!!.textSize = 35F
            //mDots[i]!!.setTextColor(resources.getColor(R.color.colorTransparentWhite))
            mDots[i]!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorTransparentWhite))

            mDotsLayout.addView(mDots[i])
        }

        if (mDots.isNotEmpty()) {
            //mDots[position]!!.setTextColor(resources.getColor(R.color.colorWhite))
            mDots[position]!!.setTextColor(ContextCompat.getColor(applicationContext, R.color.colorWhite))
        }
    }

    private var viewListener: ViewPager.OnPageChangeListener = object : ViewPager.OnPageChangeListener {
        override fun onPageScrolled(i: Int, v: Float, i1: Int) {}

        override fun onPageSelected(i: Int) {
            addDotsIndicator(i)
            if (i == 2) {
                YoYo.with(Techniques.Tada)
                    .duration(700)
                    .playOn(btRegister)

                vibrate()
            }
        }

        override fun onPageScrollStateChanged(i: Int) {}
    }

    private fun fromHtml(source: String): Spanned {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(source, Html.FROM_HTML_MODE_LEGACY)
        } else {
            Html.fromHtml(source)
        }
    }

    private fun vibrate(){
        val vibrator = application.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            vibrator.vibrate(200)
        }
    }
}
