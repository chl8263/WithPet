package com.example.withpet.util

import android.content.Context
import androidx.annotation.NonNull
import androidx.annotation.RawRes

object Util {

    fun raw2string(@NonNull context: Context, @RawRes raw_resid: Int): String {
        return try {
            val res = context.resources
            val input = res.openRawResource(raw_resid)
            val b = ByteArray(input.available())
            input.read(b)
            input.close()
            String(b)
        } catch (e: Exception) {
            ""
        }
    }
}