package com.example.coffeehouse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.example.coffeehouse.ui.theme.CoffeeHouseTheme

class MainActivity : ComponentActivity() {

    private val viewModel by lazy {
        MainViewModel(App.getInstance().userRepository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        splashScreen.setKeepOnScreenCondition { viewModel.state.keepSplash }
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CoffeeHouseTheme {
                if (viewModel.state.keepSplash.not()) {
                    Navigation(if (viewModel.state.isLoggedIn) CoffeeCatalog else Login)
                }
            }
        }
    }
}
