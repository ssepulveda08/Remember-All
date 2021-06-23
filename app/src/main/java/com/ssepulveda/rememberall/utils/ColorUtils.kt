package com.ssepulveda.rememberall.utils

import android.graphics.Color
import android.graphics.drawable.GradientDrawable

fun getHexToColor(coloHex: String): Int {
    return Color.parseColor("#$coloHex")
}

fun getCustomGradient(hex: String): GradientDrawable {
    val startColor: Int = Color.parseColor("#FFFFFF")
    val endColor: Int = Color.parseColor("#$hex")
    return GradientDrawable(GradientDrawable.Orientation.TL_BR, intArrayOf(startColor, endColor))
}
