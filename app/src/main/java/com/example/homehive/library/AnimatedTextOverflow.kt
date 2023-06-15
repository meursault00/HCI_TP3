package com.example.homehive.library

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun AnimatedTextOverflow(text: String, modifier: Modifier = Modifier, color: Color = Color(0xFFFFFFFF), style: TextStyle = TextStyle(fontSize = 20.sp)) {
    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val textWidth = remember { mutableStateOf(0) }

    LaunchedEffect(Unit) {
        coroutineScope.launch {
            while (true) {
                delay(1000)
                scrollState.animateScrollTo(
                    scrollState.maxValue,
                    animationSpec = tween(durationMillis = 2500, easing = LinearEasing)
                )
                delay(2000)
                scrollState.animateScrollTo(
                    scrollState.maxValue - textWidth.value,
                    animationSpec = tween(durationMillis = 2500, easing = LinearEasing)
                )
            }
        }
    }

    Box(
        modifier = Modifier
            .horizontalScroll(scrollState)
            .onGloballyPositioned { coordinates ->
                textWidth.value = coordinates.size.width
            }
    ) {
        Text(
            text = text,
            fontWeight = FontWeight.Bold,
            color = color,
            style = style,
            overflow = TextOverflow.Clip,
            maxLines = 1
        )
    }
}
