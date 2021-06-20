package com.ssepulveda.rememberall.db.repositories

import androidx.lifecycle.LiveData
import com.ssepulveda.rememberall.base.repositorys.BaseItemRepository
import com.ssepulveda.rememberall.db.dao.ItemListDao
import com.ssepulveda.rememberall.db.entity.ItemList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ItemsRepository(private val itemListDao: ItemListDao) : BaseItemRepository {

    override suspend fun addNewItem(newItem: ItemList) {
        withContext(Dispatchers.IO) {
            itemListDao.setItemList(newItem)
        }
    }

    override fun getItemsByList(idList: Long): LiveData<List<ItemList>> =
        itemListDao.getItemByList(idList)

    override suspend fun deleteItem(item: ItemList) = withContext(Dispatchers.IO) {
        itemListDao.deleteItem(item)
    }

    override suspend fun updateItem(item: ItemList) = withContext(Dispatchers.IO) {
        itemListDao.updateItem(item)
    }
}
