package com.example.withpet.ui.hospital.hospitalMain.usecase

import com.example.withpet.vo.hospital.HospitalSearchDTO
import io.reactivex.Observable

interface HistoryRepository {
    fun getHistoryData() : Observable<ArrayList<HospitalSearchDTO>>
    fun insertHistory(data : HospitalSearchDTO)

}