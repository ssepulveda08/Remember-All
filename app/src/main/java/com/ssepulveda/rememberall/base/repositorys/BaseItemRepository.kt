package com.ssepulveda.rememberall.base.repositorys

import androidx.lifecycle.LiveData
import com.ssepulveda.rememberall.db.entity.ItemList

interface BaseItemRepository {
    suspend fun addNewItem(newItem: ItemList)
    fun getItemsByList(idList: Long): LiveData<List<ItemList>>
    suspend fun deleteItem(item: ItemList)
    suspend fun updateItem(item: ItemList)
}
