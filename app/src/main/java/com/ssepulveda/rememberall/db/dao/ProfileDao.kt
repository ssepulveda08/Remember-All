package com.ssepulveda.rememberall.db.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ssepulveda.rememberall.db.entity.Profile

@Dao
interface ProfileDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProfile(profile: Profile)

    @Query("SELECT name FROM profile WHERE id = 1 LIMIT 1")
    fun getNameProfile(): LiveData<String>

    @Query("SELECT name FROM profile WHERE id = 1 LIMIT 1")
    fun getNameActive(): String

    @Update
    fun updateProfile(profile: Profile)
}