package com.ssepulveda.rememberall

import android.app.Application
import androidx.appcompat.app.AppCompatDelegate
import com.flurry.android.FlurryAgent
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        FlurryAgent.Builder()
            .withLogEnabled(true)
            .build(this, getString(R.string.api_key_flurry))
    }
}
