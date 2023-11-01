package com.developer.currency.core.database

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    companion object {
        private const val PREFS_NAME = "currency"
        private const val LANG = "language"
        private const val AUTO_UPDATE = "auto_update"
        private const val THEME = "theme"
    }

    private val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLang(lang: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(LANG, lang).apply()
    }

    fun getLang(): String? {
        return sharedPref.getString(LANG, null)
    }

    fun saveAutoUpdate(bool: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(AUTO_UPDATE, bool).apply()
    }

    fun getAutoUpdate(): String? {
        return sharedPref.getString(AUTO_UPDATE, null)
    }

    fun saveTheme(theme: Boolean) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putBoolean(THEME, theme).apply()
    }

    fun getTheme(): Boolean {
        return sharedPref.getBoolean(THEME, false)
    }
}