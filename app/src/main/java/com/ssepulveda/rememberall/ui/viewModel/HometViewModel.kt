package com.ssepulveda.rememberall.ui.viewModel


import android.content.Context
import android.util.Log
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ssepulveda.rememberall.db.SharedPreferenceRepository
import com.ssepulveda.rememberall.db.entity.CounterList
import com.ssepulveda.rememberall.db.entity.ListApp
import com.ssepulveda.rememberall.db.repositories.ListRepository
import com.ssepulveda.rememberall.db.repositories.ProfileRepository
import com.ssepulveda.rememberall.ui.activities.CIPHERTEXT_WRAPPER
import com.ssepulveda.rememberall.ui.activities.SHARED_PREFS_FILENAME
import com.ssepulveda.rememberall.utils.BEEN_SHOWN_DIALOG
import com.ssepulveda.rememberall.utils.DARK_MODE
import com.ssepulveda.rememberall.utils.DOUBLE_COLUMN
import com.ssepulveda.rememberall.utils.biometric.CiphertextWrapper
import com.ssepulveda.rememberall.utils.biometric.CryptographyManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.crypto.Cipher
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val profileRepository: ProfileRepository,
    private val listRepository: ListRepository,
    private val preference: SharedPreferenceRepository,
    private val ciphertextWrapper: CiphertextWrapper?,
    private val biometricManager: BiometricManager,
    private val cryptographyManager: CryptographyManager,
) : ViewModel() {

    private val _showDialogSuggested = MutableLiveData(false)

    private val _showBiometric = MutableLiveData<Cipher>()

    init {
        initShowSuggestedDialog()
    }

    fun deleteList(counterList: CounterList) {
        viewModelScope.launch {
            listRepository.deleteList(counterList.id)
        }
    }

    fun setList(listApp: ListApp) {
        viewModelScope.launch {
            listRepository.addNewList(listApp)
        }
    }

    fun isDoubleColumn(): Boolean = preference.getBoolean(DOUBLE_COLUMN, true)

    fun isDarkMode(): Boolean = preference.getBoolean(DARK_MODE)

    fun hasEnabledBiometricsStart(): Boolean = ciphertextWrapper != null

    fun initShowSuggestedDialog() {
        viewModelScope.launch {
            val state = !preference.getBoolean(BEEN_SHOWN_DIALOG) && listRepository.isEmpty()
            _showDialogSuggested.postValue(state)
        }
    }

    fun settingDarkMode(isDarMode: Boolean) {
        preference.setValue(DARK_MODE, isDarMode)
    }

    fun settingDoubleColumn(isDarMode: Boolean) {
        preference.setValue(DOUBLE_COLUMN, isDarMode)
    }

    fun setShowDialog() {
        preference.setValue(BEEN_SHOWN_DIALOG, true)
    }

    fun updateProfile(name: String) {
        viewModelScope.launch {
            profileRepository.addProfileManager(name)
        }
    }


    /**
     * Biometric
     */

    fun showBiometricPromptForEncryption() {
        if (biometricManager.canAuthenticate() == BiometricManager.BIOMETRIC_SUCCESS) {
            val secretKeyName = SECRET_KEY
            val cipher = cryptographyManager.getInitializedCipherForEncryption(secretKeyName)
            _showBiometric.postValue(cipher)
        }
    }

    fun encryptAndStoreServerToken(
        authResult: BiometricPrompt.AuthenticationResult,
    ) {
        authResult.cryptoObject?.cipher?.apply {
            val encryptedServerTokenWrapper = cryptographyManager.encryptData(this)
            cryptographyManager.persistCiphertextWrapperToSharedPrefs(
                encryptedServerTokenWrapper,
                SHARED_PREFS_FILENAME,
                Context.MODE_PRIVATE,
                CIPHERTEXT_WRAPPER
            )
            Log.d("Biometric", "Done")
        }
    }

    fun clearBiometric() {
        cryptographyManager.clearCiphertextWrapperFromSharedPrefs(
            SHARED_PREFS_FILENAME,
            Context.MODE_PRIVATE,
            CIPHERTEXT_WRAPPER
        )
    }


    /**
     * Livedata
     */

    fun onCounterList(): LiveData<List<CounterList>> = listRepository.getCounterList()

    fun onNameProfile(): LiveData<String> = profileRepository.getNameProfile()

    fun showDialogSuggested(): LiveData<Boolean> = _showDialogSuggested

    fun showBiometric(): LiveData<Cipher> = _showBiometric
}