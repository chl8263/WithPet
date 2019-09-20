package com.example.withpet.vo.pet

import com.example.withpet.vo.hospital.HospitalSearchDTO

data class PetDTO(
        val imageUrl: String = "",                     // 사진 URL
        val name: String = "",                         // 이름
        val birthDay: String = "",                     // 생년월일
        val gender: Int = -1,                          // 0 : 남 , 1: 여
        val petNum: String? = null,                    // 동물 등록번호
        val hospital: HospitalSearchDTO? = null)       // ? Key가 필요할꺼같은데