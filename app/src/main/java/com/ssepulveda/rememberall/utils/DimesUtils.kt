package com.ssepulveda.rememberall.utils

import android.content.res.Resources

fun getDpToPx(dp: Int) : Int = (dp * Resources.getSystem().displayMetrics.density).toInt()

fun getPxToDp(px: Int) : Int = (px / Resources.getSystem().displayMetrics.density).toInt()