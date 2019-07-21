package com.example.withpet.ui.hospitalDetail.usecase

import com.example.withpet.vo.hospital.HospitalReviewDTO
import com.example.withpet.vo.hospital.HospitalStarDTO
import io.reactivex.Observable

interface HospitalStarRepository {

    fun getStarData(hospitalUid : String) : Observable<HospitalStarDTO>

}