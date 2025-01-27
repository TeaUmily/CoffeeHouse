package com.example.coffeehouse.ui.screens.login

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

class LoginViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(LoginUiState())
        private set

    fun login(username: String, password: String) {
        state = state.copy(isLoading = true)

        val error = validateCredentials(username, password)
        if (error != null) {
            state = LoginUiState(snackbarMessage = error)
            return
        }

        viewModelScope.launch {
            try {
                userRepository.login(username, password)
                state = LoginUiState(isUserLoggedIn = true, isLoading = false)
            } catch (e: Exception) {
                state = LoginUiState(snackbarMessage = e.message ?: "An error occurred", isLoading = false)
            }
        }
    }

    private fun String.isValidUsername(): String? =
        if (this.isEmpty()) "Username is empty, please fill it in" else null

    private fun String.isValidPassword(): String? =
        if (this.isEmpty()) "Password is empty, please fill it in" else null

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
                LoginViewModel(
                    App.getInstance().userRepository
                )
            }
        }
    }
}

data class LoginUiState(
    val isLoading: Boolean = false,
    val snackbarMessage: String? = null,
    val isUserLoggedIn: Boolean = false
)
