package com.example.withpet.ui.walk.usecase

import com.google.android.gms.maps.model.LatLng

interface WalkDirectionUseCase {

    fun getDirection(destinationName : String?, destination: LatLng)
}