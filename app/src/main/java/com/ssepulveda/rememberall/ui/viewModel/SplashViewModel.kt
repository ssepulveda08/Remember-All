package com.ssepulveda.rememberall.ui.viewModel

import android.os.CountDownTimer
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ssepulveda.rememberall.db.SharedPreferenceRepository
import com.ssepulveda.rememberall.utils.DARK_MODE
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.annotation.CheckReturnValue
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val preference: SharedPreferenceRepository,
) : ViewModel() {

    private val _hideProgress = MutableLiveData(false)

    init {
        initCounter()
        updateTheme()
    }

    private fun updateTheme() {
        if (preference.getBoolean(DARK_MODE, false)) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun initCounter() {
        val timer = object : CountDownTimer(4000, 1000) {
            override fun onTick(millisUntilFinished: Long) {}

            override fun onFinish() {
                _hideProgress.postValue(true)
            }
        }
        timer.start()
    }

    @CheckReturnValue
    fun onHideProgress(): LiveData<Boolean> = _hideProgress
}