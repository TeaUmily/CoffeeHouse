package com.example.coffeehouse.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = Coffee80,
    secondary = Brown80,
    tertiary = Beige80,

    background = Color(0xFF121212), // Dark background color
    surface = Color(0xFF1F1F1F), // Dark surface color
    onPrimary = Color.Black, // Black text on primary
    onSecondary = Color.Black, // Black text on secondary
    onTertiary = Color.Black, // Black text on tertiary
    onBackground = Color(0xFFF1F1F1), // Light text on dark background
    onSurface = Color(0xFFF1F1F1) // Light text on dark surface
)

private val LightColorScheme = lightColorScheme(
    primary = Coffee40,
    secondary = Brown40,
    tertiary = Beige40,

    // Custom background and surface colors for light theme
    background = Color(0xFFFFFBFE), // Light background
    surface = Color(0xFFFFFBFE), // Light surface
    onPrimary = Color.White, // On primary text
    onSecondary = Color.White, // On secondary text
    onTertiary = Color.White, // On tertiary text
    onBackground = Color(0xFF1C1B1F), // On background dark text
    onSurface = Color(0xFF1C1B1F), // On surface dark text
)

@Composable
fun CoffeeHouseTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.surface.toArgb()
            window.navigationBarColor = colorScheme.surface.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
        }
    }
    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}