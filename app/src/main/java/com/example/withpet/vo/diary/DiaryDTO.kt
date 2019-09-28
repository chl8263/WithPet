package com.example.withpet.vo.diary

import java.io.Serializable


data class DiaryDTO(val content: String = "",
                    val imageUrl: String = "",
                    val date: String = "",
                    var dummyHeader: Int = 0,
                    val createDate: Long = System.currentTimeMillis()) : Serializable