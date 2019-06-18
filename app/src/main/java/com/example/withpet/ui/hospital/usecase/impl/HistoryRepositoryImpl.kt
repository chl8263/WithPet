package com.example.withpet.ui.hospital.usecase.impl

import com.example.withpet.ui.hospital.usecase.HistoryRepository
import com.example.withpet.util.DBManager
import com.example.withpet.vo.HospitalSearchDTO
import io.reactivex.Observable

class HistoryRepositoryImpl(var dbManager : DBManager) : HistoryRepository{
    override fun getHistoryData(): Observable<ArrayList<HospitalSearchDTO>> {
        return Observable.create {
            emitter ->
            emitter.onNext(dbManager.selectHospitalHistory())
        }
    }

    override fun insertHistory() {

    }
}