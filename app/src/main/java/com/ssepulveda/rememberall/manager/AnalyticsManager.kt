package com.ssepulveda.rememberall.manager

import android.util.Log
import com.flurry.android.FlurryAgent

private val TAG = AnalyticsManager::class.java.simpleName

class AnalyticsManager {

    fun eventRegistration(nameEvent: String, params: Map<String, String>) {
        Log.d(TAG, "Event: $nameEvent params $params")
        FlurryAgent.logEvent(nameEvent, params)
    }

    fun eventRegistration(nameEvent: String) {
        Log.d(TAG, "Event: $nameEvent")
        FlurryAgent.logEvent(nameEvent)
    }
}
