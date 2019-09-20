package com.example.withpet.ui.walk.usecase

import com.example.withpet.vo.walk.WalkBicycleDTO
import com.example.withpet.vo.walk.WalkParkDTO
import io.reactivex.Observable
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import retrofit2.Call

interface WalkUseCase {

    fun insertBicycleList(): Single<Boolean>

    fun insertParkList(): Single<Boolean>

    fun searchBicycleList(keyword: String): Single<MutableList<WalkBicycleDTO>>

    fun searchParkList(keyword: String): Single<MutableList<WalkParkDTO>>

    fun getBicycleList(): Single<List<WalkBicycleDTO>>

    fun getParkList(): Single<List<WalkParkDTO>>

    fun getDirection(destinationName : String?, destination: LatLng)
}