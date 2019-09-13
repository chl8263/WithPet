package com.example.withpet.ui.hospital.hospitalMain.usecase

import com.example.withpet.vo.LocationVO
import io.reactivex.Observable

interface LocationUseCase {
    fun getCurrentLocation() : Observable<LocationVO>
}