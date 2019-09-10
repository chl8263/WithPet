package com.example.withpet.util

import androidx.annotation.NonNull
import java.text.DecimalFormat

object Formatter {
    fun f(@NonNull num: Number, prefix: String = "", suffix: String = ""): String = "$prefix${DecimalFormat("#,##.##").format(num)}$suffix"
}