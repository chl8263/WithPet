package com.example.withpet.ui.walk.usecase.impl

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.example.withpet.ui.walk.usecase.WalkDirectionUseCase
import com.google.android.gms.maps.model.LatLng


class WalkDirectionUseCaseImpl(var context: Context) : WalkDirectionUseCase {

    override fun getDirection(destinationName: String?, destination: LatLng) {
        val url =
            "https://map.kakao.com/link/to/${destinationName ?: ""},${destination.latitude},${destination.longitude}"
        val urlIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url)).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(urlIntent)
    }
}