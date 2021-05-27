package com.ssepulveda.rememberall.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.db.entity.ListAppCount

@Dao
interface ListAppDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setListApp(list: ListApp)

    @Query("SELECT COUNT(id) FROM list_app ORDER BY id ASC")
    fun getCountListApp(): Int

    @Query("SELECT id, name, color,(SELECT  COUNT(itemId) FROM item_list WHERE listId = id)  as count from list_app ORDER BY id ASC")
    fun getListAppAndCount(): LiveData<List<ListAppCount>>

    @Query("SELECT id, name, color from list_app WHERE  id = :listId LIMIT 1")
    fun getListById(listId: Long): LiveData<ListApp>

    @Query("DELETE FROM list_app WHERE id = :id ;")
    fun deleteList(id: Long)
}