package com.ssepulveda.rememberall.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.rememberall.db.entity.ItemList

@Dao
interface ItemListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setItemList(movie: ItemList)

    @Query("SELECT * from item_list WHERE listId = :id ORDER BY itemId ASC")
    fun getItemByList(id: Long): LiveData<List<ItemList>>

    @Delete
    fun deleteItem(itemList: ItemList)

    @Update
    fun updateItem(itemList: ItemList)
}
