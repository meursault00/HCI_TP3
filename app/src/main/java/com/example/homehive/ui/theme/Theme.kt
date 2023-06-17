package com.example.homehive.ui.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat
import com.example.homehive.viewmodels.isDarkTheme

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFF1A3830), //FF203831 // INTERESANTE = FF4F7469
    secondary = Color(0xFF202020), // HAY OPACOS
    tertiary = Color(0xFF526863),
    background = Color(0xFFA8A495), // BOTONES APAGADOS Y COSASS ASI
    surface = Color(0xFFFFFBFE),
    onPrimary = Color(0xFFDADFDD),
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF325052),
    onSurface = Color(0xFFFFFFFF),
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF4F7469), //FF203831 // INTERESANTE = FF4F7469 // FF84A89D
    secondary = Color(0xFFEFE5C5), // HAY OPACOS
    tertiary = Color(0xFFF4CF6D),
    background = Color(0xFF918A76), // BOTONES APAGADOS Y COSASS ASI
    surface = Color(0xFFFFFBFE), // BLANCO SPEAKER
    onPrimary = Color(0xFF203831),
    onSecondary = Color(0xFF795454), // ROUTINES
    onTertiary = Color.White,
    onBackground = Color(0xFFA0CCCF), // TAP BACKGROUND
    onSurface = Color(0xFF000000),

)

@Composable
fun HomeHiveTheme(
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme.value) {
        DarkColorScheme
    } else {
        LightColorScheme
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !isDarkTheme.value
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}



