package com.example.coffeehouse.ui.screens.catalog

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.coffeehouse.App
import com.example.coffeehouse.data.model.response.CoffeeItem
import com.example.coffeehouse.data.repository.CoffeeHouseRepository
import com.example.coffeehouse.data.repository.CoffeeRepository
import com.example.coffeehouse.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.HttpException

class CoffeeCatalogViewModel(
    private val coffeeRepository: CoffeeRepository,
    private val userRepository: UserRepository,
    private val coffeeHouseRepository: CoffeeHouseRepository
) : ViewModel() {

    var coffeeCatalogUiState: CoffeeCatalogUiState by mutableStateOf(CoffeeCatalogUiState.Loading)

    fun getCoffeeCatalog() {
        viewModelScope.launch {
            try {
                val favoriteCoffees = coffeeHouseRepository.getFavoriteCoffeeIds()
                val listResult = coffeeRepository.getCoffees().map { it.toCatalogCoffee(favoriteCoffees) }
                val userName = userRepository.getUserName()
                coffeeCatalogUiState = CoffeeCatalogUiState.Success(
                    coffees = listResult,
                    title = "Hello $userName",
                    isRefreshing = false
                )
            } catch (e: Exception) {
                coffeeCatalogUiState = if (e is HttpException && e.code() == 401) {
                    CoffeeCatalogUiState.LoggedOut
                } else {
                    CoffeeCatalogUiState.Error
                }
            }
        }
    }

    fun onFavoriteClick(coffee: CatalogCoffee) {
        viewModelScope.launch {
            try {
                if (coffee.isFavorite.not()) {
                    coffeeHouseRepository.addFavoriteCoffee(coffee.id)
                } else {
                    coffeeHouseRepository.removeFavoriteCoffee(coffee.id)
                }
                updateFavoriteStatus(coffee)
            } catch (e: Exception) {
                // Silently fail, it's ok, ❤️
            }
        }
    }

    fun onLogoutClick() {
        viewModelScope.launch {
            userRepository.logout()
            coffeeCatalogUiState = CoffeeCatalogUiState.LoggedOut
        }
    }

    fun onRefresh() {
        (coffeeCatalogUiState as? CoffeeCatalogUiState.Success)?.let {
            coffeeCatalogUiState = it.copy(isRefreshing = true)
            getCoffeeCatalog()
        }
    }

    private fun updateFavoriteStatus(coffee: CatalogCoffee) {
        (coffeeCatalogUiState as? CoffeeCatalogUiState.Success)?.let { state ->
            val updatedList = state.coffees.map { if (it.id == coffee.id) it.copy(isFavorite = !it.isFavorite) else it }
            coffeeCatalogUiState = state.copy(coffees = updatedList)
        }
    }

    private fun CoffeeItem.toCatalogCoffee(favoriteCoffees: List<String>): CatalogCoffee {
        return CatalogCoffee(
            id = id,
            name = name,
            description = description,
            imageUrl = imageUrl,
            price = price,
            roastLevel = roastLevel,
            isFavorite = favoriteCoffees.contains(id)
        )
    }

    companion object {
        val Factory = viewModelFactory {
            initializer {
                CoffeeCatalogViewModel(
                    App.getInstance().coffeeRepository,
                    App.getInstance().userRepository,
                    App.getInstance().coffeeHouseRepository
                )
            }
        }
    }
}

sealed class CoffeeCatalogUiState {
    data object Loading : CoffeeCatalogUiState()
    data class Success(
        val coffees: List<CatalogCoffee>,
        val title: String = "",
        val isRefreshing: Boolean = false
    ) : CoffeeCatalogUiState()
    data object Error : CoffeeCatalogUiState()
    data object LoggedOut : CoffeeCatalogUiState()
}

data class CatalogCoffee(
    val id: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val price: Double,
    val roastLevel: Int,
    var isFavorite: Boolean
)
