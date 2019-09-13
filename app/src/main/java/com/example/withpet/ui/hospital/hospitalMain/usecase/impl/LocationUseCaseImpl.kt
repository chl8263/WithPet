package com.example.withpet.ui.hospital.hospitalMain.usecase.impl

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.widget.Toast
import com.example.withpet.ui.hospital.hospitalMain.usecase.LocationUseCase
import com.example.withpet.vo.LocationVO
import io.reactivex.Observable

class LocationUseCaseImpl(var context : Context) : LocationUseCase {

    private val lm : LocationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager

    /*
    *   TODO
    *   Last Location 을 처음 불러온뒤 현재 위치를 검색하여 지도에 이동하는 방식으로 해야 좀더 빠른 UI를 보여줄 수 있음
    * */
    @SuppressLint("MissingPermission")
    override fun getCurrentLocation(): Observable<LocationVO> {
        return Observable.create {
            emitter ->
            if(lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)){

                lm.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 100 , 10F , object : LocationListener{

                    override fun onLocationChanged(location: Location?) {
                        // just get once
                        lm.removeUpdates(this)

                        val longitude = location!!.longitude
                        val latitude = location!!.latitude

                        var currentLocation = LocationVO(longitude = longitude , latitude = latitude)

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