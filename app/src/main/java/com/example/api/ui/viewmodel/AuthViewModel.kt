package com.example.api.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.api.data.local.AuthPreferences
import com.example.api.data.repository.AuthRepository
import com.example.api.ui.state.AuthUiState

class AuthViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AuthRepository(AuthPreferences(application))

    private val _uiState = MutableLiveData(createInitialState())
    val uiState: LiveData<AuthUiState> = _uiState

    fun onUsernameChange(value: String) {
        updateState { copy(username = value, errorMessage = null, successMessage = null) }
    }

    fun onPasswordChange(value: String) {
        updateState { copy(password = value, errorMessage = null, successMessage = null) }
    }

    fun onConfirmPasswordChange(value: String) {
        updateState { copy(confirmPassword = value, errorMessage = null, successMessage = null) }
    }

    fun showRegister() {
        updateState {
            copy(
                isRegisterMode = true,
                password = "",
                confirmPassword = "",
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun showLogin() {
        updateState {
            copy(
                isRegisterMode = false,
                password = "",
                confirmPassword = "",
                errorMessage = null,
                successMessage = null
            )
        }
    }

    fun register() {
        val state = _uiState.value ?: return
        val username = state.username.trim()
        val password = state.password.trim()
        val confirmPassword = state.confirmPassword.trim()

        when {
            username.isEmpty() -> setError("Escriu un usuari")
            password.isEmpty() -> setError("Escriu una contrasenya")
            confirmPassword.isEmpty() -> setError("Repeteix la contrasenya")
            password != confirmPassword -> setError("Les contrasenyes no coincideixen")
            else -> {
                repository.register(username, password)
                _uiState.value = AuthUiState(
                    username = username,
                    isRegisterMode = false,
                    isLoggedIn = true,
                    currentUser = username,
                    successMessage = "Usuari registrat"
                )
            }
        }
    }

    fun login() {
        val state = _uiState.value ?: return
        val username = state.username.trim()
        val password = state.password.trim()

        when {
            username.isEmpty() -> setError("Escriu un usuari")
            password.isEmpty() -> setError("Escriu una contrasenya")
            !repository.login(username, password) -> setError("Usuari o contrasenya incorrectes")
            else -> {
                _uiState.value = state.copy(
                    password = "",
                    confirmPassword = "",
                    isLoggedIn = true,
                    currentUser = username,
                    errorMessage = null,
                    successMessage = "Sessio iniciada"
                )
            }
        }
    }

    fun logout() {
        repository.logout()
        _uiState.value = AuthUiState(
            username = repository.getSavedUsername(),
            isRegisterMode = !repository.hasSavedUser(),
            isLoggedIn = false,
            currentUser = "",
            successMessage = "Sessio tancada"
        )
    }

    private fun createInitialState(): AuthUiState {
        val hasSavedUser = repository.hasSavedUser()
        val isLoggedIn = repository.isLoggedIn()
        val savedUsername = repository.getSavedUsername()

        return AuthUiState(
            username = savedUsername,
            isRegisterMode = !hasSavedUser,
            isLoggedIn = isLoggedIn,
            currentUser = if (isLoggedIn) savedUsername else ""
        )
    }

    private fun setError(message: String) {
        updateState {
            copy(
                errorMessage = message,
                successMessage = null
            )
        }
    }

    private fun updateState(transform: AuthUiState.() -> AuthUiState) {
        val currentState = _uiState.value ?: createInitialState()
        _uiState.value = currentState.transform()
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                AuthViewModel(this.requireApplication())
            }
        }
    }
}

private fun CreationExtras.requireApplication(): Application {
    return this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY]
        ?: error("Application no disponible")
}
