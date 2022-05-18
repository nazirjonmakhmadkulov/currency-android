package com.developer.valyutaapp.utils

import android.content.Context
import android.content.SharedPreferences

class SharedPreference(context: Context) {

    private val PREFS_NAME = "qwangy"
    private val PREFS_TOKEN_VALUE = "access_token"
    private val PREFS_REFRESH_TOKEN_VALUE = "refresh_token"
    private val PREFS_USER_ID_VALUE = "user_id"

    val sharedPref: SharedPreferences =
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    fun saveToken(token_value: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(PREFS_TOKEN_VALUE, token_value).apply()
    }

    fun getToken(): String {
        return sharedPref.getString(PREFS_TOKEN_VALUE, "")!!
    }

    fun saveRefreshToken(refresh_token: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(PREFS_REFRESH_TOKEN_VALUE, refresh_token).apply()
    }

    fun getRefreshToken(): String {
        return sharedPref.getString(PREFS_REFRESH_TOKEN_VALUE, "")!!
    }

    fun saveUserId(userId: String) {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(PREFS_USER_ID_VALUE, userId).apply()
    }

    fun getUserId(): String {
        return sharedPref.getString(PREFS_USER_ID_VALUE, "")!!
    }

    fun deleteToken() {
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.remove(PREFS_NAME).clear().apply()
    }
}