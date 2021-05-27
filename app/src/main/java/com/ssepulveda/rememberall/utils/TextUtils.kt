package com.ssepulveda.rememberall.utils


fun ucFirst(str: String): String {
    return if (str.isBlank()) {
        str
    } else {
        str.substring(0, 1).toUpperCase() + str.substring(1)
    }
}

