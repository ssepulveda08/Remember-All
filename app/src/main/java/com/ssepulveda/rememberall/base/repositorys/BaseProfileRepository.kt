package com.ssepulveda.rememberall.base.repositorys

import androidx.lifecycle.LiveData

interface BaseProfileRepository {
    suspend fun initProfile(name: String)
    suspend fun updateProfile(name: String)
    fun getNameProfile(): LiveData<String>
    suspend fun addProfileManager(name: String)
}
