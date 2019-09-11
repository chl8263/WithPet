package com.example.withpet.vo.pat

import com.example.withpet.vo.LocationVO

data class PatDTO(
        val imageUrl: String,                   // 사진 URL
        val name: String,                       // 이름
        val birthDay: String,                   // 생년월일
        val gender: Int,                        // 0 : 남 , 1: 여
        val patNum: String?,                    // 동물 등록번호
        val hospital: Map<String, LocationVO>?) // ? Key가 필요할꺼같은데