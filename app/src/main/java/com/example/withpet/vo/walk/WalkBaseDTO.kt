package com.example.withpet.vo.walk

import android.os.Parcelable
import com.example.withpet.ui.walk.enums.eWalkType
import com.google.android.gms.maps.model.LatLng

abstract class WalkBaseDTO(
        val type: eWalkType,
        open val _name: String? = "",
        open val _longitude: String? = null,
        open val _latitude: String? = null,
        open val _imageUrl : String? = null
) : Parcelable {

    val location: LatLng
        get() = LatLng((_latitude?.takeIf { it.isNotEmpty() }?: "0.0").toDouble(), (_longitude?.takeIf { it.isNotEmpty() } ?: "0.0").toDouble())

    abstract fun extractDetailList() : ArrayList<WalkDetailRowDTO>
}