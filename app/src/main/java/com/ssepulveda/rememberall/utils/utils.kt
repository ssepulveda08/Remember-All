package com.ssepulveda.rememberall.utils

private const val SIZE = 12

fun randomString(): String = List(SIZE) {
    (('a'..'z') + ('A'..'Z') + ('0'..'9')).random()
}.joinToString("")