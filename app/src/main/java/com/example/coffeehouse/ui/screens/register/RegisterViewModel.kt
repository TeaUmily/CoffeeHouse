package com.example.coffeehouse.ui.screens.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coffeehouse.App
import com.example.coffeehouse.data.repository.UserRepository
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(RegisterUiState())
        private set

    fun register(username: String, password: String) {
        state = state.copy(isLoading = true)

        val error = validateCredentials(username, password)
        if (error != null) {
            state = RegisterUiState(snackbarMessage = error)
            return
        }

        viewModelScope.launch {
            try {
                userRepository.register(username, password)
                userRepository.login(username, password)
                state = RegisterUiState(isUserRegistered = true, isLoading = false)
            } catch (e: Exception) {
                state = RegisterUiState(snackbarMessage = e.message ?: "An error occurred", isLoading = false)
            }
        }
    }

    private fun String.isValidUsername(): String? {
        return when {
            this.isEmpty() -> "Username is empty, please fill it in"
            this.length < 4 -> "Username is too short, it must be at least 4 characters"
            this.length > 20 -> "Username is too long, it must be no more than 20 characters"
            !this.matches(Regex("^[a-zA-Z0-9]*$")) -> "Username should only contain letters and numbers"
            else -> null
        }
    }

    private fun String.isValidPassword(): String? {
        return when {
            this.isEmpty() -> "Password is empty, please fill it in"
            this.length < 4 -> "Password is too short, it must be at least 4 characters"
            this.length > 20 -> "Password is too long, it must be no more than 20 characters"
            !this.matches(Regex("^[a-zA-Z0-9]*$")) -> "Password should only contain letters and numbers"
            else -> null
        }
    }

    private fun validateCredentials(username: String, password: String): String? {
        username.isValidUsername()?.let { return it }
        password.isValidPassword()?.let { return it }
        return null
    }

    fun clearSnackbar() {
        state = state.copy(snackbarMessage = null)
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                RegisterViewModel(
                    App.getInstance().userRepository
                )
            }
        }
    }
}

data class RegisterUiState(
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null,
    val isUserRegistered: Boolean = false
)
