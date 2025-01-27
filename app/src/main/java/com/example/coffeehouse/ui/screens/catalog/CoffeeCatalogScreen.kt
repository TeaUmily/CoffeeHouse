package com.example.coffeehouse.ui.screens.catalog

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoffeeCatalogScreen(
    coffeeCatalogUiState: CoffeeCatalogUiState,
    retry: () -> Unit,
    logOut: () -> Unit,
    onFavoriteClick: (CatalogCoffee) -> Unit,
    onLogOut: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    val currentOnUserLogOut by rememberUpdatedState(onLogOut)

    LaunchedEffect(coffeeCatalogUiState)  {
        if (coffeeCatalogUiState is CoffeeCatalogUiState.LoggedOut) {
            currentOnUserLogOut()
        }
    }

    Scaffold(
        modifier = modifier
            .nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            if (coffeeCatalogUiState is CoffeeCatalogUiState.Success || coffeeCatalogUiState is CoffeeCatalogUiState.Loading) {
                CoffeeCatalogTopAppBar(
                    scrollBehavior = scrollBehavior,
                    onLogoutClick = logOut,
                    title = when (coffeeCatalogUiState) {
                        is CoffeeCatalogUiState.Success -> coffeeCatalogUiState.title
                        else -> "Loading best coffee"
                    },
                )
            }
        }
    ) {
        Surface(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
        ) {
            when (coffeeCatalogUiState) {
                is CoffeeCatalogUiState.Loading -> CoffeeCatalogLoading(modifier = modifier.fillMaxSize())
                is CoffeeCatalogUiState.Success -> CoffeeCatalogResult(
                    coffeeCatalogUiState.coffees,
                    modifier = modifier.fillMaxWidth(),
                    onFavoriteClick = onFavoriteClick,
                    onRefresh = onRefresh,
                    isRefreshing = coffeeCatalogUiState.isRefreshing
                )
                is CoffeeCatalogUiState.Error -> ErrorScreen(retryAction = retry, modifier = modifier.fillMaxSize())
                is CoffeeCatalogUiState.LoggedOut -> Unit
            }
        }
    }
}
