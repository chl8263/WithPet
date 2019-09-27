package com.example.withpet.vo.diary


data class DiaryDTO(val title: String = "",
                    val content: String = "",
                    val imageUrl: String = "",
                    val date: String = "",
                    val createDate: Long = System.currentTimeMillis())