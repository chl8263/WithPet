package com.example.withpet.ui.hospital.usecase

import com.example.withpet.vo.HospitalSearchDTO
import io.reactivex.Observable
import java.util.*
import kotlin.collections.ArrayList

interface HospitalRepository {

    fun getHospitalSearchData(searchValue : String) : Observable<ArrayList<HospitalSearchDTO>>
}