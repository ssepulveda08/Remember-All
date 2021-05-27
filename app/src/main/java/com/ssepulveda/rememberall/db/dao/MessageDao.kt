package com.ssepulveda.rememberall.db.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ssepulveda.rememberall.db.entity.Message

@Dao
interface MessageDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun setMessage(movie: Message)

    @Query("SELECT * from message_table ORDER BY id ASC")
    fun getMessages() : LiveData<List<Message>>

    @Query("DELETE FROM message_table")
    fun deleteAll()

}