package com.example.withpet.vo.walk

data class WalkDetailLowDTO(private val pair: Pair<String, String>) {
    val label: String
        get() = pair.first
    val content: String
        get() = pair.second
}