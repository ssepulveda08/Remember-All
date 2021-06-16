package com.ssepulveda.rememberall.ui.activities

import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.biometric.BiometricPrompt
import androidx.core.view.isVisible
import com.ssepulveda.rememberall.base.BaseActivity
import com.ssepulveda.rememberall.databinding.ActivitySplashBinding
import com.ssepulveda.rememberall.ui.activities.models.StartActivityModel
import com.ssepulveda.rememberall.ui.viewModel.SplashViewModel
import com.ssepulveda.rememberall.utils.BiometricAuthentication
import com.ssepulveda.rememberall.utils.BiometricCallback
import com.ssepulveda.rememberall.utils.biometric.BiometricPromptUtils
import dagger.hilt.android.AndroidEntryPoint
import javax.crypto.Cipher

const val SHARED_PREFS_FILENAME = "biometric_prefs"
const val CIPHERTEXT_WRAPPER = "ciphertext_wrapper"

@AndroidEntryPoint
class SplashActivity : BaseActivity(), BiometricCallback {

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding

    private lateinit var biometricAuthentication: BiometricAuthentication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        biometricAuthentication = BiometricAuthentication(this, lifecycle, this)
        subscribe()
    }

    private fun subscribe() {
        viewModel.onHideProgress().observe(this, ::updateUI)
        viewModel.showBiometric().observe(this, ::showBiometricPrompt)
    }

    private fun updateUI(boolean: Boolean) {
        if (boolean) {
            launchHomeActivity()
        }
    }

    private fun showBiometricPrompt(cipher: Cipher) {
        biometricAuthentication.createPrompt(cipher)
    }

    private fun launchHomeActivity() {
        binding.progressBar.isVisible = false
        startActivity(StartActivityModel(HomeActivity::class.java))
        finish()
    }

    override fun biometricError(throwable: Throwable) {
        Log.e("SplashActivity", throwable.message.toString())
        finish()
    }

    override fun biometricSuccess(result: BiometricPrompt.AuthenticationResult) {
        viewModel.decryptServerTokenFromStorage(result)
    }

}