package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkParkDTO
import io.reactivex.Observable

interface WalkUseCase {

    fun insertBicycleList() : Observable<Boolean>

    fun searchWalkList(keyword : String) : Observable<MutableList<WalkBicycleDTO>>

    fun getBicycleList() : Observable<List<WalkBicycleDTO>>

    fun getParkList() : Observable<List<WalkParkDTO>>
}