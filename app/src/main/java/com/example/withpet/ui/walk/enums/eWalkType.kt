package com.example.withpet.ui.walk.enums

import androidx.annotation.DrawableRes
import com.example.withpet.R

@Suppress("ClassName")
enum class eWalkType(val displayName: String, @DrawableRes val icon: Int) {
    PARK("공원", R.drawable.walk_park),
    TRAIL("산책로", R.drawable.walk_trail),
    DEFAULT("", R.drawable.walk_default);

    companion object {
        fun getEnumByIndex(index: Int): eWalkType {
            return when (index) {
                0 -> PARK
                1 -> TRAIL
                else -> DEFAULT
            }
        }
    }
}