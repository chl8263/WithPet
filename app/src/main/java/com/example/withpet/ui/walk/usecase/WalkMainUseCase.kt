package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.walk.WalkParkDTO
import com.example.withpet.vo.walk.WalkTrailDTO
import io.reactivex.Single

interface WalkMainUseCase {

    fun insertParkList()

    fun insertTrailList()

    fun searchParkList(keyword: String): Single<MutableList<WalkParkDTO>>

    fun searchTrailList(keyword: String): Single<MutableList<WalkTrailDTO>>

    fun getParkList(): Single<List<WalkParkDTO>>

    fun getTrailList(): Single<List<WalkTrailDTO>>
}