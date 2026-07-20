package com.redfrogec.credigo.domain.utils

import kotlin.math.round

fun roundBigDecimalToTwoDecimals(value: Double): Double {
    return round(value * 100) / 100
}

fun Double.formatTwoDecimals(): String {
    val rounded = round(this * 100) / 100.0
    val parts = rounded.toString().split(".")
    val integerPart = parts[0]
    val fractionalPart = if (parts.size > 1) parts[1].padEnd(2, '0').substring(0, 2) else "00"
    return "$integerPart.$fractionalPart"
}
