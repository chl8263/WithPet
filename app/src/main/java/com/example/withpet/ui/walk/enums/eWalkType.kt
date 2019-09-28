package com.example.withpet.ui.walk.enums

import androidx.annotation.DrawableRes
import com.example.withpet.R

@Suppress("ClassName")
enum class eWalkType(val displayName: String, @DrawableRes val icon: Int) {
    BICYCLE("자전거도로", R.drawable.walk_bicycle),
    PARK("공원", R.drawable.walk_park),
    DEFAULT("", R.drawable.walk_default);

    companion object {
        fun getEnumByIndex(index: Int): eWalkType {
            return when (index) {
                0 -> BICYCLE
                1 -> PARK
                else -> DEFAULT
            }
        }
    }
}