package com.qi0.weslley.estudo_introviewwithbuttons_kotlin

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView

class SliderAdapter(internal var context: Context) : PagerAdapter() {

    private lateinit var layoutInflater: LayoutInflater

    private var slider_images = intArrayOf(

        R.drawable.group10, R.drawable.group11, R.drawable.group12
    )

    private var slider_heading = arrayOf(

        "EAT", "SLEEP", "CODE"
    )

    private var slider_desc = arrayOf(

        "EAT DESC", "SLEEP DESC", "CODE DESC"
    )

    override fun getCount(): Int {
        return slider_heading.size
    }

    override fun isViewFromObject(view: View, o: Any): Boolean {
        return view === o
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {

        layoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val view = layoutInflater.inflate(R.layout.slider_layout, container, false)

        val sliderImageView = view.findViewById<ImageView>(R.id.sliderImage)
        val sliderHeader = view.findViewById<TextView>(R.id.sliderHeader)
        val sliderDesc = view.findViewById<TextView>(R.id.sliderDesc)

        sliderImageView.setImageResource(slider_images[position])
        sliderHeader.text = slider_heading[position]
        sliderDesc.text = slider_desc[position]

        //view.setBackgroundColor(slider_background_color[position]);

        container.addView(view)

        return view
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}
