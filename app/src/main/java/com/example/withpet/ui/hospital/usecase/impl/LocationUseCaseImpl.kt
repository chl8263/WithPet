package com.example.withpet.ui.hospital.usecase.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import androidx.core.content.getSystemService
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.vo.LocationVo
import io.reactivex.Observable
import org.koin.android.ext.android.inject

class LocationUseCaseImpl(var context : Context) : LocationUseCase {

    private val lm : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Observable<LocationVo> {
        return Observable.create {
            emitter ->
            val location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER)

            val longitude = location.longitude
            val latitude = location.latitude

            var currentLocation = LocationVo(longitude = longitude , latitude = latitude)

            emitter.onNext(currentLocation)
        }
    }
}