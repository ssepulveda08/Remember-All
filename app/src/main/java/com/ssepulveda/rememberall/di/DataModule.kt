package com.ssepulveda.rememberall.di

import android.content.SharedPreferences
import com.ssepulveda.rememberall.db.SharedPreferenceRepository
import com.ssepulveda.rememberall.db.database.AppDatabase
import com.ssepulveda.rememberall.db.repositories.ItemsRepository
import com.ssepulveda.rememberall.db.repositories.ListRepository
import com.ssepulveda.rememberall.db.repositories.ProfileRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun profileRepositoryProvider(
        appDatabase: AppDatabase
    ) = ProfileRepository(appDatabase.profileDao())

    @Provides
    fun listRepositoryProvider(
        appDatabase: AppDatabase
    ) = ListRepository(appDatabase.listAppDao())

    @Provides
    fun itemRepositoryProvider(
        appDatabase: AppDatabase
    ) = ItemsRepository(appDatabase.itemListDao())

    @Provides
    fun sharedPreferenceProvider(
        preference: SharedPreferences,
        editor: SharedPreferences.Editor
    ): SharedPreferenceRepository = SharedPreferenceRepository(preference, editor)

}