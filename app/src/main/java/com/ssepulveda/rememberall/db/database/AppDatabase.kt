package com.ssepulveda.rememberall.db.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ssepulveda.rememberall.db.dao.ItemListDao
import com.ssepulveda.rememberall.db.dao.ListAppDao
import com.ssepulveda.rememberall.db.dao.MessageDao
import com.ssepulveda.rememberall.db.dao.ProfileDao
import com.ssepulveda.rememberall.db.entity.ItemList
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.db.entity.Message
import com.ssepulveda.rememberall.db.entity.Profile

@Database(
    entities = [Message::class, ListApp::class, ItemList::class, Profile::class],
    version = 1,
    exportSchema = false
)

abstract class AppDatabase : RoomDatabase() {
    abstract fun messageDao(): MessageDao
    abstract fun listAppDao(): ListAppDao
    abstract fun itemListDao(): ItemListDao
    abstract fun profileDao(): ProfileDao
}
