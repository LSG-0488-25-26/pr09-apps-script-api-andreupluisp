package com.example.api.data.local

import android.content.Context
import android.content.SharedPreferences

class AuthPreferences(context: Context) {

    private val prefs: SharedPreferences = context.getSharedPreferences(
        PREFS_NAME,
        Context.MODE_PRIVATE
    )

    fun hasSavedUser(): Boolean {
        return prefs.contains(KEY_USERNAME) && prefs.contains(KEY_PASSWORD)
    }

    fun getSavedUsername(): String {
        return prefs.getString(KEY_USERNAME, "") ?: ""
    }

    fun getSavedPassword(): String {
        return prefs.getString(KEY_PASSWORD, "") ?: ""
    }

    fun isLoggedIn(): Boolean {
        return prefs.getBoolean(KEY_LOGGED_IN, false)
    }

    fun saveUser(username: String, password: String) {
        prefs.edit()
            .putString(KEY_USERNAME, username)
            .putString(KEY_PASSWORD, password)
            .putBoolean(KEY_LOGGED_IN, true)
            .apply()
    }

    fun setLoggedIn(isLoggedIn: Boolean) {
        prefs.edit()
            .putBoolean(KEY_LOGGED_IN, isLoggedIn)
            .apply()
    }

    companion object {
        private const val PREFS_NAME = "auth_prefs"
        private const val KEY_USERNAME = "username"
        private const val KEY_PASSWORD = "password"
        private const val KEY_LOGGED_IN = "logged_in"
    }
}
