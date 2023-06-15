package com.example.homehive

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


// desde afuera lo llamas como:
// val windowInfo = rememberWindowInfo()
// y podes saber el tamaño o comparar asi:
// if (windowInfo.screenWidthInfo is WindowInfo.WindowType.Compact) {}  -> significa que la pantala es chica

@Composable
fun rememberWindowInfo(): WindowInfo {
    val configuration = LocalConfiguration.current
    return WindowInfo(
        screenWidthInfo = when {
            configuration.screenWidthDp < 600 -> WindowInfo.WindowType.Compact
            configuration.screenWidthDp < 665 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeightInfo = when {
            configuration.screenHeightDp < 480 -> WindowInfo.WindowType.Compact
            configuration.screenHeightDp < 900 -> WindowInfo.WindowType.Medium
            else -> WindowInfo.WindowType.Expanded
        },
        screenHeight = configuration.screenHeightDp.dp,
        screenWidth = configuration.screenWidthDp.dp
    )
}

data class WindowInfo(
    val screenWidthInfo: WindowType,
    val screenHeightInfo: WindowType,
    val screenWidth : Dp,
    val screenHeight : Dp,
){
    sealed class WindowType{
        object Compact: WindowType()
        object Medium: WindowType()
        object Expanded: WindowType()
    }
}