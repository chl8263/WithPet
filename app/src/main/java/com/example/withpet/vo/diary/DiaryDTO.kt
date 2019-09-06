package com.example.withpet.vo.diary

data class DiaryDTO(val content: String,
                    val tag: ArrayList<String>,
                    val imageUrl: ArrayList<String>,
                    val createDate: String)