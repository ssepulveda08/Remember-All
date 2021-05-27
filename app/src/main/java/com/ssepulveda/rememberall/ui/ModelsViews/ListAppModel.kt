package com.ssepulveda.rememberall.ui.ModelsViews

import com.ssepulveda.rememberall.db.entity.ListAppCount

data class ListAppModel(
    val model: ListAppCount,
    var showDelete: Boolean = false
)