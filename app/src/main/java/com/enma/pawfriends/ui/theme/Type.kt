package com.enma.pawfriends.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.enma.pawfriends.R


// Set of Material typography styles to start with

private val Kanit = FontFamily(
    Font(R.font.kanit_regular, FontWeight.Normal),
    Font(R.font.kanit_medium, FontWeight.Medium),
    Font(R.font.kanit_semibold, FontWeight.SemiBold),
    Font(R.font.kanit_extralight, FontWeight.ExtraLight),
    Font(R.font.kanit_bold, FontWeight.Bold)
)


val Typography = Typography(
    bodyMedium = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.SemiBold,
        fontSize = 25.sp

    ),
    bodyLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        letterSpacing = 0.5.sp
    ),
    bodySmall = TextStyle(
        fontFamily = Kanit,
        fontWeight = FontWeight.Normal,
        fontSize = 28.sp
    )

    /* Other default text styles to override
    titleLarge = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Normal,
        fontSize = 22.sp,
        lineHeight = 28.sp,
        letterSpacing = 0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )
    */
)