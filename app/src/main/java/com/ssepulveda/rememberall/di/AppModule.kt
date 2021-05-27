package com.ssepulveda.rememberall.di

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.ssepulveda.rememberall.db.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    @Singleton
    fun databaseProvider(app: Application) = Room.databaseBuilder(
        app,
        AppDatabase::class.java,
        "movie-db"
    ).build()

    @Provides
    @Singleton
    fun preferenceProvider(app: Application): SharedPreferences =
        app.getSharedPreferences("settings_app", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    fun editorProvider(preference: SharedPreferences): SharedPreferences.Editor = preference.edit()

}