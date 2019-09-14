package com.example.withpet.vo.walk

data class WalkDetailRowDTO(private val pair: Pair<String, String?>) {
    val label: String
        get() = pair.first
    val content: String?
        get() = pair.second
}