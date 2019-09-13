package com.example.withpet.ui.hospital.hospitalMain.usecase

import com.example.withpet.vo.hospital.HospitalSearchDTO
import io.reactivex.Observable
import kotlin.collections.ArrayList

interface HospitalRepository {

    fun getHospitalSearchData(searchValue : String) : Observable<ArrayList<HospitalSearchDTO>>
}