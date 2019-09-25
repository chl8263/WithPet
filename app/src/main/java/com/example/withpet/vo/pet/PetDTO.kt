package com.example.withpet.vo.pet

import com.example.withpet.vo.hospital.HospitalSearchDTO
import java.io.Serializable

data class PetDTO(
        val imageUrl: String = "",                                                      // 사진 URL
        val name: String = "",                                                          // 이름
        val birthDay: String = "",                                                      // 생년월일
        val gender: Int = -1,                                                           // 0 : 남 , 1: 여
        val petNum: String? = null,                                                     // 동물 등록번호
        val hospital: HospitalSearchDTO? = null,                                        // 병원
        val createDate: Long = System.currentTimeMillis()                               // 생성일
) : Serializable