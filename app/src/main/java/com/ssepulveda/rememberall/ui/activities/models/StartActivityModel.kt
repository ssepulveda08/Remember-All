package com.ssepulveda.rememberall.ui.activities.models

import android.os.Bundle

data class StartActivityModel(
    val activity: Class<*>,
    var bundle: Bundle? = null,
    var code: Int = 0,
    var flags: Int = 0
)