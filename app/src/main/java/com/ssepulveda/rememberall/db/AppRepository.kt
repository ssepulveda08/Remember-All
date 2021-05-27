package com.ssepulveda.rememberall.db

import com.ssepulveda.rememberall.db.dao.ItemListDao
import com.ssepulveda.rememberall.db.dao.ListAppDao
import com.ssepulveda.rememberall.db.dao.MessageDao
import com.ssepulveda.rememberall.db.database.AppDatabase
import com.ssepulveda.rememberall.db.entity.ItemList
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.db.entity.Message
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(val dataBase: AppDatabase) {

    private var messageDao: MessageDao = dataBase.messageDao()

    private var listAppDao: ListAppDao = dataBase.listAppDao()

    private var itemListDao: ItemListDao = dataBase.itemListDao()

    fun getMessages() = messageDao.getMessages()

    fun getListAppAndCount() = listAppDao.getListAppAndCount()

    fun getItemsByList(listId: Long) = itemListDao.getItemByList(listId)

    suspend fun getCountList(): Int =  withContext(Dispatchers.IO) { listAppDao.getCountListApp() }

    fun getListById(id: Long) = listAppDao.getListById(id)

    suspend fun setMessage(message: Message) {
        setMessageBG(message)
    }

    suspend fun setListApp(list: ListApp) {
        setListAppBG(list)
    }

    suspend fun setItemList(item: ItemList) {
        setItemListBG(item)
    }

    suspend fun deleteItem(item: ItemList) {
        onDeleteItem(item)
    }

    suspend fun deleteList(id: Long) {
        onDeleteList(id)
    }

    suspend fun updateItem(item: ItemList) {
        onUpdateItem(item)
    }

    private suspend fun setMessageBG(message: Message) {
        withContext(Dispatchers.IO) {
            messageDao.setMessage(message)
        }
    }

    private suspend fun setListAppBG(list: ListApp) {
        withContext(Dispatchers.IO) {
            listAppDao.setListApp(list)
        }
    }

    private suspend fun setItemListBG(item: ItemList) {
        withContext(Dispatchers.IO) {
            itemListDao.setItemList(item)
        }
    }

    private suspend fun onDeleteItem(item: ItemList) {
        withContext(Dispatchers.IO) {
            itemListDao.deleteItem(item)
        }
    }

    private suspend fun onDeleteList(id: Long) {
        withContext(Dispatchers.IO) {
            listAppDao.deleteList(id)
        }
    }

    private suspend fun onUpdateItem(item: ItemList) {
        withContext(Dispatchers.IO) {
            itemListDao.updateItem(item)
        }
    }
}