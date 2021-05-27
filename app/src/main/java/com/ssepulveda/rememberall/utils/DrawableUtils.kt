package com.ssepulveda.rememberall.utils

import android.graphics.PorterDuff
import android.graphics.PorterDuffColorFilter
import android.graphics.drawable.Drawable

fun changeColorDrawable(drawable: Drawable?, hex: String): Drawable? {
    val color = getHexToColor(hex)
    drawable?.colorFilter = PorterDuffColorFilter(color, PorterDuff.Mode.SRC_ATOP)
    return drawable
}