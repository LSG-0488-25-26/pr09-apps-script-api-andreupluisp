package com.example.api.data.repository

import com.example.api.data.local.AuthPreferences

class AuthRepository(
    private val authPreferences: AuthPreferences
) {

    fun hasSavedUser(): Boolean {
        return authPreferences.hasSavedUser()
    }

    fun isLoggedIn(): Boolean {
        return authPreferences.isLoggedIn()
    }

    fun getSavedUsername(): String {
        return authPreferences.getSavedUsername()
    }

    fun register(username: String, password: String) {
        authPreferences.saveUser(username, password)
    }

    fun login(username: String, password: String): Boolean {
        val savedUsername = authPreferences.getSavedUsername()
        val savedPassword = authPreferences.getSavedPassword()
        val isValidUser = username == savedUsername && password == savedPassword

        authPreferences.setLoggedIn(isValidUser)
        return isValidUser
    }

    fun logout() {
        authPreferences.setLoggedIn(false)
    }
}
