package com.ssepulveda.rememberall.di

import android.content.SharedPreferences
import com.ssepulveda.rememberall.db.AppRepository
import com.ssepulveda.rememberall.db.ProfileRepository
import com.ssepulveda.rememberall.db.SharedPreferenceRepository
import com.ssepulveda.rememberall.db.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun appRepositoryProvider(
        appDatabase: AppDatabase
    ) = AppRepository(appDatabase)

    @Provides
    fun profileRepositoryProvider(
        appDatabase: AppDatabase
    ) = ProfileRepository(appDatabase)

    @Provides
    fun sharedPreferenceProvider(
        preference: SharedPreferences,
        editor: SharedPreferences.Editor
    ): SharedPreferenceRepository = SharedPreferenceRepository(preference, editor)


}