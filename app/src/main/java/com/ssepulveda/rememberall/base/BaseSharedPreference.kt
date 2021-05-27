package com.ssepulveda.rememberall.base

interface BaseSharedPreference {
    fun getBoolean(key: String, default: Boolean = false): Boolean

    fun getString(key: String, default: String? = null): String?

    fun getInt(key: String, default: Int = 0): Int

    fun setBoolean(key: String, value: Boolean)

    fun setString(key: String, value: String)

    fun setInt(key: String, value: Int)

    fun setValue(key: String, value: Any)
}