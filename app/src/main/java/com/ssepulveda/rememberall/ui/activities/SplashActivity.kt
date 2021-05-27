package com.ssepulveda.rememberall.ui.activities

import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import com.ssepulveda.rememberall.base.BaseActivity
import com.ssepulveda.rememberall.databinding.ActivitySplashBinding
import com.ssepulveda.rememberall.ui.activities.models.StartActivityModel
import com.ssepulveda.rememberall.ui.viewModel.SplashViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    private val viewModel: SplashViewModel by viewModels()

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)
        subscribe()
    }

    private fun subscribe() {
        viewModel.onHideProgress().observe(this, ::updateUI)
    }

    private fun updateUI(boolean: Boolean) {
        if (boolean) {
            binding.progressBar.isVisible = false
            startActivity(StartActivityModel(HomeActivity::class.java))
            finish()
        }
    }

}