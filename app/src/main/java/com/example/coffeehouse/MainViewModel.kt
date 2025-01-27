package com.example.coffeehouse

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.coffeehouse.data.repository.UserRepository
import kotlinx.coroutines.launch

class MainViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    var state by mutableStateOf(MainState())
        private set

    init {
        checkIfUserIsLoggedIn()
    }

    private fun checkIfUserIsLoggedIn() {
        viewModelScope.launch {
            state = if (userRepository.isUserLoggedIn()) {
                state.copy(isLoggedIn = true, keepSplash = false)
            } else {
                state.copy(isLoggedIn = false, keepSplash = false)
            }
        }
    }
}

data class MainState(
    val keepSplash: Boolean = true,
    val isLoggedIn: Boolean = false
)