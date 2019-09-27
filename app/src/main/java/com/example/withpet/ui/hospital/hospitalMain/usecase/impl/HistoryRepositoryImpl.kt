package com.example.withpet.ui.hospital.hospitalMain.usecase.impl

import com.example.withpet.ui.hospital.hospitalMain.usecase.HistoryRepository
import com.example.withpet.util.DBManager
import com.example.withpet.vo.hospital.HospitalSearchDTO
import io.reactivex.Observable

class HistoryRepositoryImpl(val dbManager : DBManager) : HistoryRepository{

    override fun getHistoryData(): Observable<ArrayList<HospitalSearchDTO>> {
        return Observable.create {
            emitter ->
            emitter.onNext(dbManager.selectHospitalHistory())
        }
    }

    override fun insertHistory(data : HospitalSearchDTO) {

        dbManager.insertHospitalHistory(data)
    }
}