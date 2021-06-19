package com.ssepulveda.rememberall.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import com.ssepulveda.rememberall.utils.biometric.BiometricPromptUtils
import javax.crypto.Cipher

class BiometricAuthentication(
    private val activity: AppCompatActivity,
    private val lifecycle: Lifecycle,
    private val callback: BiometricCallback? = null,
) : LifecycleObserver {

    private lateinit var biometricPrompt: BiometricPrompt

    fun createPrompt(cipher: Cipher) {
        biometricPrompt = BiometricPromptUtils.createBiometricPrompt(
            activity,
            callback
        )
        val promptInfo = BiometricPromptUtils.createPromptInfo(activity)
        biometricPrompt.authenticate(promptInfo, BiometricPrompt.CryptoObject(cipher))
    }

}

interface BiometricCallback {
    fun biometricError(throwable: Throwable)
    fun biometricSuccess(result: BiometricPrompt.AuthenticationResult)
}