package com.example.homehive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp


@Composable
fun SpeakerScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(top = 70.dp)
    ) {
        Text("I AM INSIDE A SPEAKER")

    }
}