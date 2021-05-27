package com.ssepulveda.rememberall.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ssepulveda.rememberall.db.entity.ItemList

@Dao
interface ItemListDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setItemList(movie: ItemList)

    @Query("SELECT * from item_list WHERE listId = :id ORDER BY itemId ASC")
    fun getItemByList(id: Long) : LiveData<List<ItemList>>

    @Delete
    fun deleteItem(itemList: ItemList)

    @Update
    fun updateItem(itemList: ItemList)

    /*@Query("UPDATE item_list SET text = :newText WHERE itemId = :id")
    fun updateItem(id: Long, newText)*/



    //@Delete
    //fun deleteItem(item: ItemList)
}