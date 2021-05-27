package com.ssepulveda.rememberall.db

import com.ssepulveda.rememberall.db.dao.ProfileDao
import com.ssepulveda.rememberall.db.database.AppDatabase
import com.ssepulveda.rememberall.db.entity.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProfileRepository(val dataBase: AppDatabase) {

    private var profile: ProfileDao = dataBase.profileDao()

    fun getNameProfile() = profile.getNameProfile()

    suspend fun updateNameProfile(name: String) {
        withContext(Dispatchers.IO) {
            val currentName = profile.getNameActive()
            if (currentName.isNullOrBlank()) {
                setRowProfile(name)
            } else {
                updateProfile(name)
            }
        }
    }

    private suspend fun updateProfile(name: String) {
        withContext(Dispatchers.IO) {
            profile.updateProfile(Profile(1, name))
        }
    }

    private suspend fun setRowProfile(name: String) {
        withContext(Dispatchers.IO) {
            profile.insertProfile(Profile(1, name))
        }
    }
}