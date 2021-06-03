package com.ssepulveda.rememberall.base.repositorys

import androidx.lifecycle.LiveData
import com.ssepulveda.rememberall.db.entity.CounterList
import com.ssepulveda.rememberall.db.entity.ListApp

interface BaseListRepository {
    suspend fun isEmpty(): Boolean
    suspend fun addNewList(newList: ListApp)
    fun getCounterList(): LiveData<List<CounterList>>
    fun getListById(id: Long): LiveData<ListApp>
    suspend fun deleteList(id: Long)
}
