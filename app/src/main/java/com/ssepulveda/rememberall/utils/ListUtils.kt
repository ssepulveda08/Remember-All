package com.ssepulveda.rememberall.utils

import android.content.Context
import com.ssepulveda.rememberall.R

fun getColorList(): List<String> = listOf(
    "4caf50",
    "64b5f6",
    "4db6ac",
    "00bcd4",
    "e6ee9c",
    "43a047",
    "ff9800",
    "ff9860"
)

fun getSuggestedList(context: Context?) =
    context?.resources?.getStringArray(R.array.suggested_array)?.toList() ?: emptyList()
