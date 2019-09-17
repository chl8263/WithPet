package com.example.withpet.ui.walk.enums

@Suppress("ClassName")
enum class eWalkType(val displayName: String) {
    BICYCLE("자전거도로"), PARK("공원"), DEFAULT("");

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