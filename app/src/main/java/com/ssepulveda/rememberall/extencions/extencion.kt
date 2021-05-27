package com.ssepulveda.rememberall.extencions


fun Long?.isZero(): Boolean = (this == null || this.toInt() == 0)
