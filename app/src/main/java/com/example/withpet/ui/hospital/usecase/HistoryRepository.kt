package com.example.withpet.ui.hospital.usecase

import com.example.withpet.vo.HospitalSearchDTO
import com.example.withpet.vo.LocationVO
import io.reactivex.Observable

interface HistoryRepository {
    fun getHistoryData() : Observable<ArrayList<HospitalSearchDTO>>
    fun insertHistory()

}