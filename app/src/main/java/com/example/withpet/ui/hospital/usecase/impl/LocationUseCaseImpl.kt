package com.example.withpet.ui.hospital.usecase.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import androidx.core.content.getSystemService
import com.example.withpet.ui.hospital.usecase.LocationUseCase
import com.example.withpet.util.Log
import com.example.withpet.vo.LocationVo
import com.google.android.material.snackbar.Snackbar
import io.reactivex.Observable
import org.koin.android.ext.android.inject

class LocationUseCaseImpl(var context : Context) : LocationUseCase {

    private val lm : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Observable<LocationVo> {
        return Observable.create {
            emitter ->
            if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100 , 10F , object : LocationListener{

                    override fun onLocationChanged(location: Location?) {
                        // just get once
                        lm.removeUpdates(this)

                        val longitude = location!!.longitude
                        val latitude = location!!.latitude

                        var currentLocation = LocationVo(longitude = longitude , latitude = latitude)

                        emitter.onNext(currentLocation)
                    }
                    override fun onStatusChanged(p0: String?, p1: Int, p2: Bundle?) {}
                    override fun onProviderEnabled(p0: String?) {}
                    override fun onProviderDisabled(p0: String?) {}
                })
            }else {
                Toast.makeText(context,"Gps , Network  위치 설정 에러입니다. ", Toast.LENGTH_SHORT).show()
            }
        }
    }
}