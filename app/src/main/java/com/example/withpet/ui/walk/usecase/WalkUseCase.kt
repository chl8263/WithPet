package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.walk.WalkBicycleDTOList
import io.reactivex.Observable

interface WalkUseCase {

    fun insertBicycleList() : Observable<Boolean>

    fun getBicycleList() : Observable<WalkBicycleDTOList>
}