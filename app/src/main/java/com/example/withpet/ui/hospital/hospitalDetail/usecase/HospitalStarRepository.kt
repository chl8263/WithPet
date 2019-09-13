package com.example.withpet.ui.hospital.hospitalDetail.usecase

import com.example.withpet.vo.hospital.HospitalStarDTO
import io.reactivex.Observable

interface HospitalStarRepository {

    fun getStarData(hospitalUid : String) : Observable<HospitalStarDTO>

}