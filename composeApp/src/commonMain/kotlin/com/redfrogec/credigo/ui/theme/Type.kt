package com.redfrogec.credigo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import credigo.composeapp.generated.resources.Res
import credigo.composeapp.generated.resources.montserrat_bold
import credigo.composeapp.generated.resources.montserrat_light
import credigo.composeapp.generated.resources.montserrat_medium
import credigo.composeapp.generated.resources.montserrat_regular
import credigo.composeapp.generated.resources.montserrat_semibold
import org.jetbrains.compose.resources.Font

@Composable
fun getMonserratFamily() = FontFamily(
    Font(Res.font.montserrat_bold, FontWeight.Bold),
    Font(Res.font.montserrat_light, FontWeight.Light),
    Font(Res.font.montserrat_medium, FontWeight.Medium),
    Font(Res.font.montserrat_semibold, FontWeight.SemiBold),
    Font(Res.font.montserrat_regular, FontWeight.Normal),
)
@Composable
fun typography() = Typography(

    displaySmall = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 36.sp,
        lineHeight = 44.sp,
        letterSpacing = 0.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    ),
    titleLarge = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 26.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    titleMedium = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 22.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.15.sp
    ),
    titleSmall = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.30.sp
    ),
    labelLarge = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 18.sp,
        lineHeight = 22.sp,
        letterSpacing = 0.5.sp
    ),
    labelMedium = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 16.sp,
        lineHeight = 20.sp,
        letterSpacing = 0.5.sp
    ),
    labelSmall = TextStyle(
        fontFamily = getMonserratFamily(),
        fontWeight = FontWeight.Bold,
        fontSize = 14.sp,
        lineHeight = 18.sp,
        letterSpacing = 0.5.sp,
    )
)

