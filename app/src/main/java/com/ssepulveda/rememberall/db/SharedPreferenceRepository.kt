package com.ssepulveda.rememberall.db

import android.content.SharedPreferences
import com.ssepulveda.rememberall.base.BaseSharedPreference

class SharedPreferenceRepository(
    private val preference: SharedPreferences,
    private val editor: SharedPreferences.Editor
) : BaseSharedPreference {

    override fun getBoolean(key: String, default: Boolean): Boolean =
        preference.getBoolean(key, default)

    override fun getString(key: String, default: String?): String? =
        preference.getString(key, default)

    override fun getInt(key: String, default: Int): Int = preference.getInt(key, default)

    override fun setBoolean(key: String, value: Boolean) {
        editor.putBoolean(key, value)
        editor.commit()
    }

    override fun setString(key: String, value: String) {
        editor.putString(key, value)
        editor.commit()
    }

    override fun setInt(key: String, value: Int) {
        editor.putInt(key, value)
        editor.commit()
    }

    override fun setValue(key: String, value: Any) {
        when (value) {
            is Boolean -> setBoolean(key, value)
            is String -> setString(key, value)
            is Int -> setInt(key, value)
        }
    }
}
