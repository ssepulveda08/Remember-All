package com.ssepulveda.rememberall.db.repositories

import androidx.lifecycle.LiveData
import com.ssepulveda.rememberall.base.repositorys.BaseProfileRepository
import com.ssepulveda.rememberall.db.dao.ProfileDao
import com.ssepulveda.rememberall.db.entity.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

private const val ID_PROFILE: Long = 1

class ProfileRepository(private val profileDao: ProfileDao) : BaseProfileRepository {

    override suspend fun initProfile(name: String) {
        withContext(Dispatchers.IO) {
            profileDao.insertProfile(Profile(ID_PROFILE, name))
        }
    }

    override suspend fun updateProfile(name: String) {
        withContext(Dispatchers.IO) {
            profileDao.updateProfile(Profile(ID_PROFILE, name))
        }
    }

    override fun getNameProfile(): LiveData<String> = profileDao.getNameProfile()

    override suspend fun addProfileManager(name: String) {
        withContext(Dispatchers.IO) {
            val currentName = profileDao.getNameActive()
            if (currentName.isNullOrBlank()) {
                initProfile(name)
            } else {
                updateProfile(name)
            }
        }
    }
}
