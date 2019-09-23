package com.example.withpet.util

import com.google.android.gms.maps.model.LatLng
import kotlin.math.acos
import kotlin.math.cos
import kotlin.math.sin

object DistanceUtil {

    fun getDistance(origin: LatLng?, destination: LatLng, unit: eDistanceUnit): String {

        if(origin == null){
            return ""
        }

        val theta = origin.longitude - destination.longitude
        var dist = sin(deg2rad(origin.latitude)) * sin(deg2rad(destination.latitude)) + cos(deg2rad(origin.latitude)) * cos(deg2rad(destination.latitude)) * cos(deg2rad(theta))

        dist = acos(dist)
        dist = rad2deg(dist)
        dist *= 60.0 * 1.1515

        return when (unit) {
            eDistanceUnit.kilometer -> Formatter.f(prefix = "직선거리 기준 약 ", suffix = "km", num = dist * 1.609344)
            eDistanceUnit.meter -> Formatter.f(prefix = "직선거리 기준 약 ", suffix = "m", num = dist * 1609.344)
        }
    }

    // This function converts decimal degrees to radians
    private fun deg2rad(deg: Double): Double {
        return deg * Math.PI / 180.0
    }

    // This function converts radians to decimal degrees
    private fun rad2deg(rad: Double): Double {
        return rad * 180 / Math.PI
    }

    @Suppress("ClassName", "EnumEntryName")
    enum class eDistanceUnit {
        kilometer, meter;
    }
}