package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkParkDTO
import io.reactivex.Single

interface WalkMainUseCase {

    fun insertBicycleList(): Single<Boolean>

    fun insertParkList(): Single<Boolean>

    fun searchBicycleList(keyword: String): Single<MutableList<WalkBicycleDTO>>

    fun searchParkList(keyword: String): Single<MutableList<WalkParkDTO>>

    fun getBicycleList(): Single<List<WalkBicycleDTO>>

    fun getParkList(): Single<List<WalkParkDTO>>
}