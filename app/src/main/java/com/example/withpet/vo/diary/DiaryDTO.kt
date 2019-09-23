package com.example.withpet.vo.diary

import com.example.withpet.util.SDF

data class DiaryDTO(val title: String,
                    val content: String,
                    val imageUrl: String,
                    val date: String,
                    val createDate: String = SDF.yyyymmdd_2.format(System.currentTimeMillis()))