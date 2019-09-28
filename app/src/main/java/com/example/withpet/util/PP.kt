package com.example.withpet.util

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

@Suppress("PrivatePropertyName", "SpellCheckingInspection", "FunctionName")
enum class PP {
    ;

    private val DEFVALUE_STRING = ""
    private val DEFVALUE_FLOAT = -1f
    private val DEFVALUE_INT = -1
    private val DEFVALUE_LONG = -1L
    private val DEFVALUE_BOOLEAN = false

    companion object {
        private lateinit var PREFERENCES: SharedPreferences

        fun CREATE(context: Context) {
            PREFERENCES = PreferenceManager.getDefaultSharedPreferences(context)
        }
    }

    fun set(v: Boolean) {
        PREFERENCES.edit().putBoolean(name, v).apply()
    }

    fun set(v: Int) {
        PREFERENCES.edit().putInt(name, v).apply()
    }

    fun set(v: Long) {
        PREFERENCES.edit().putLong(name, v).apply()
    }

    fun set(v: Float) {
        PREFERENCES.edit().putFloat(name, v).apply()
    }

    fun set(v: String) {
        PREFERENCES.edit().putString(name, v).apply()
    }

    fun set(v: Set<String>) {
        PREFERENCES.edit().putStringSet(name, v).apply()
    }

    operator fun get(defValue: String): String? = getString(defValue)

    fun get(): String? = getString()

    fun getBoolean(): Boolean = getBoolean(DEFVALUE_BOOLEAN)

    fun getInt(): Int = getInt(DEFVALUE_INT)

    fun getLong(): Long = getLong(DEFVALUE_LONG)

    fun getFloat(): Float = getFloat(DEFVALUE_FLOAT)

    fun getString(): String? = getString(DEFVALUE_STRING)

    fun getStringSet(): Set<String>? = getStringSet(null)

    fun getBoolean(defValues: Boolean): Boolean = PREFERENCES.getBoolean(name, defValues)

    fun getInt(defValues: Int): Int = PREFERENCES.getInt(name, defValues)

    fun getLong(defValues: Long): Long = PREFERENCES.getLong(name, defValues)

    fun getFloat(defValues: Float): Float = PREFERENCES.getFloat(name, defValues)


    fun getString(defValues: String): String? = PREFERENCES.getString(name, defValues)

    fun getStringSet(defValues: Set<String>?): Set<String>? = PREFERENCES.getStringSet(name, defValues)
}