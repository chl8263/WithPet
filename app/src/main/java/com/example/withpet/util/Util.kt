package com.example.withpet.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import androidx.annotation.NonNull
import androidx.annotation.RawRes

object Util {

    private lateinit var DISPLAY_METRICS: DisplayMetrics

    fun CREATE(context: Context) {
        val display_metrics = context.resources.displayMetrics
        DISPLAY_METRICS = display_metrics
    }

    fun getDisplayMetrics() : DisplayMetrics = DISPLAY_METRICS

    fun raw2string(@NonNull context: Context, @RawRes raw_resid: Int): String {
        return try {
            val res = context.resources
            val input = res.openRawResource(raw_resid)
            val b = ByteArray(input.available())
            input.read(b)
            input.close()
            String(b)
        } catch (e: Exception) {
            ""
        }
    }

    fun dp2px(dp: Float): Int = TypedValue.applyDimension(1, dp, DISPLAY_METRICS).toInt()
}