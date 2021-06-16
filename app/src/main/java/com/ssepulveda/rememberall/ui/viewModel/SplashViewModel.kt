package com.ssepulveda.rememberall.ui.viewModel

import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatDelegate
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssepulveda.rememberall.db.SharedPreferenceRepository
import com.ssepulveda.rememberall.utils.DARK_MODE
import com.ssepulveda.rememberall.utils.biometric.CiphertextWrapper
import com.ssepulveda.rememberall.utils.biometric.CryptographyManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.annotation.CheckReturnValue
import javax.crypto.Cipher
import javax.inject.Inject

const val SECRET_KEY = "remember_all_encryption_key"

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preference: SharedPreferenceRepository,
    private val ciphertextWrapper: CiphertextWrapper?,
    private val biometricManager: BiometricManager,
    private val cryptographyManager: CryptographyManager,
) : ViewModel() {

    private val _hideProgress = MutableLiveData(false)

    private val _showBiometric = MutableLiveData<Cipher>()

    init {
        initBiometric()
        updateTheme()
    }

    private fun initBiometric() {
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            if (ciphertextWrapper != null) {
                showBiometricPromptForDecryption()
            } else {
                initCounter()
            }
        } else {
            initCounter()
        }
    }

    private fun updateTheme() {
        if (preference.getBoolean(DARK_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

     private fun showBiometricPromptForDecryption() {
        ciphertextWrapper?.let { textWrapper ->
            val secretKeyName = SECRET_KEY
            val cipher = cryptographyManager.getInitializedCipherForDecryption(
                secretKeyName, textWrapper.initializationVector
            )
            _showBiometric.postValue(cipher)
        }
    }

    private fun initCounter() {
        val timer = object : CountDownTimer(3000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                _hideProgress.postValue(true)
            }
        }
        timer.start()
    }

    fun decryptServerTokenFromStorage(authResult: BiometricPrompt.AuthenticationResult) {
        ciphertextWrapper?.let { textWrapper ->
            authResult.cryptoObject?.cipher?.let {
                val plaintext = cryptographyManager.decryptData(textWrapper.ciphertext, it)
                _hideProgress.postValue(true)
            }
        }
    }

    @CheckReturnValue
    fun onHideProgress(): LiveData<Boolean> = _hideProgress

    @CheckReturnValue
    fun showBiometric(): LiveData<Cipher> = _showBiometric
}