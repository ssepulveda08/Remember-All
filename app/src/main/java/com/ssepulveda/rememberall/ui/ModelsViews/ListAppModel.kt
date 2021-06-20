package com.ssepulveda.rememberall.ui.ModelsViews

import com.ssepulveda.rememberall.db.entity.CounterList

data class ListAppModel(
    val model: CounterList,
    var showDelete: Boolean = false
)
