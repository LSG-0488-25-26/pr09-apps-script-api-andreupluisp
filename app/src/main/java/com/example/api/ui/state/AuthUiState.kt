package com.example.api.ui.state

data class AuthUiState(
    val username: String = "",
    val password: String = "",
    val confirmPassword: String = "",
    val isRegisterMode: Boolean = false,
    val isLoggedIn: Boolean = false,
    val currentUser: String = "",
    val errorMessage: String? = null,
    val successMessage: String? = null
)
