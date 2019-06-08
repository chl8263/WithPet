package com.example.withpet.ui.hospital.usecase

import com.example.withpet.vo.LocationVo
import io.reactivex.Observable

interface LocationUseCase {
    fun getCurrentLocation() : Observable<LocationVo>
}