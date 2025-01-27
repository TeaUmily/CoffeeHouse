package com.example.coffeehouse

import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.coffeehouse.ui.screens.catalog.CoffeeCatalogScreen
import com.example.coffeehouse.ui.screens.catalog.CoffeeCatalogViewModel
import com.example.coffeehouse.ui.screens.login.LoginScreen
import com.example.coffeehouse.ui.screens.login.LoginViewModel
import com.example.coffeehouse.ui.screens.register.RegisterScreen
import com.example.coffeehouse.ui.screens.register.RegisterViewModel
import kotlinx.serialization.Serializable

@Composable
fun Navigation(startDestination: Route) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        composable<CoffeeCatalog> {
            val coffeeCatalogViewModel = viewModel<CoffeeCatalogViewModel>(factory = CoffeeCatalogViewModel.Factory)
            LaunchedEffect(Unit) { coffeeCatalogViewModel.getCoffeeCatalog() }
            CoffeeCatalogScreen(
                coffeeCatalogUiState = coffeeCatalogViewModel.coffeeCatalogUiState,
                retry = coffeeCatalogViewModel::getCoffeeCatalog,
                onFavoriteClick = coffeeCatalogViewModel::onFavoriteClick,
                logOut = coffeeCatalogViewModel::onLogoutClick,
                onLogOut = {
                    navController.navigate(Login) {
                        popUpTo(CoffeeCatalog) { inclusive = true }
                    }
                },
                onRefresh = coffeeCatalogViewModel::onRefresh
            )
        }

        composable<Login> {
            val loginViewModel = viewModel<LoginViewModel>(factory = LoginViewModel.Factory)
            LoginScreen(
                loginUiState = loginViewModel.state,
                login = loginViewModel::login,
                clearSnackbar = loginViewModel::clearSnackbar,
                navigateToRegister = { navController.navigate(Register) },
                onLogIn = {
                    navController.navigate(CoffeeCatalog) {
                        popUpTo(Login) { inclusive = true }
                    }
                }
            )
        }

        composable<Register> {
            val registerViewModel = viewModel<RegisterViewModel>(factory = RegisterViewModel.Factory)
            RegisterScreen(
                registerUiState = registerViewModel.state,
                register = registerViewModel::register,
                clearSnackbar = registerViewModel::clearSnackbar,
                navigateBack = { navController.navigateUp() },
                onUserRegister = {
                    navController.navigate(CoffeeCatalog) {
                        popUpTo(Register) { inclusive = true }
                    }
                }
            )
        }
    }
}

interface Route

@Serializable
data object Login : Route

@Serializable
data object Register : Route

@Serializable
data object CoffeeCatalog : Route

