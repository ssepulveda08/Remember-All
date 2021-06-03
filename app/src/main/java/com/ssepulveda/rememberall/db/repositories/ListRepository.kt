package com.ssepulveda.rememberall.db.repositories

import androidx.lifecycle.LiveData
import com.ssepulveda.rememberall.base.repositorys.BaseListRepository
import com.ssepulveda.rememberall.db.dao.ListAppDao
import com.ssepulveda.rememberall.db.entity.CounterList
import com.ssepulveda.rememberall.db.entity.ListApp
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ListRepository(private val listAppDao: ListAppDao) : BaseListRepository {

    override suspend fun isEmpty(): Boolean = withContext(Dispatchers.IO) {
        listAppDao.getCountListApp() <= 0
    }

    override suspend fun addNewList(newList: ListApp) {
        withContext(Dispatchers.IO) {
            listAppDao.setListApp(newList)
        }
    }

    override fun getCounterList(): LiveData<List<CounterList>> = listAppDao.getListAppAndCount()

    override fun getListById(id: Long): LiveData<ListApp> = listAppDao.getListById(id)

    override suspend fun deleteList(id: Long) {
        withContext(Dispatchers.IO) {
            listAppDao.deleteList(id)
        }
    }
}