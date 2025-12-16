package com.redfrogec.credigo.domain.utils

import kotlin.math.round

fun roundBigDecimalToTwoDecimals(value: Double): Double {
    return round(value * 100) / 100
}