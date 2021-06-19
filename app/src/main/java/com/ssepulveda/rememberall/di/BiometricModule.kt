package com.ssepulveda.rememberall.di

import android.app.Application
import android.content.Context
import androidx.biometric.BiometricManager
import com.ssepulveda.rememberall.ui.activities.CIPHERTEXT_WRAPPER
import com.ssepulveda.rememberall.ui.activities.SHARED_PREFS_FILENAME
import com.ssepulveda.rememberall.utils.biometric.CiphertextWrapper
import com.ssepulveda.rememberall.utils.biometric.CryptographyManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class BiometricModule {

    @Provides
    fun cryptographyManagerProvider(app: Application): CryptographyManager = CryptographyManager(app)

    @Provides
    fun ciphertextWrapperProvider(
        cryptographyManager: CryptographyManager
    ): CiphertextWrapper? = cryptographyManager.getCiphertextWrapperFromSharedPrefs(
        SHARED_PREFS_FILENAME,
        Context.MODE_PRIVATE,
        CIPHERTEXT_WRAPPER
    )

    @Provides
    fun biometricManagerProvider(
        app: Application
    ): BiometricManager = BiometricManager.from(app)



}