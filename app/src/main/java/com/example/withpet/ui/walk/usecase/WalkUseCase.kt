package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.WalkBicycleDTOList
import io.reactivex.Observable

interface WalkUseCase {
    fun getBicycleList() : Observable<WalkBicycleDTOList>
}