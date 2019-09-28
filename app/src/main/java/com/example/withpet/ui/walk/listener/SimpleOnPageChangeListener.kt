package com.example.withpet.ui.walk.listener

import androidx.viewpager.widget.ViewPager

open class SimpleOnPageChangeListener : ViewPager.OnPageChangeListener {

    var onPageScrollStateChanged: ((state: Int) -> Unit)? = null
    var onPageScrolled: ((position: Int, positionOffset: Float, positionOffsetPixels: Int) -> Unit)? = null
    var onPageSelected: ((position: Int) -> Unit)? = null

    override fun onPageScrollStateChanged(state: Int) {
        onPageScrollStateChanged?.invoke(state)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        onPageScrolled?.invoke(position, positionOffset, positionOffsetPixels)
    }

    override fun onPageSelected(position: Int) {
        onPageSelected?.invoke(position)
    }
}