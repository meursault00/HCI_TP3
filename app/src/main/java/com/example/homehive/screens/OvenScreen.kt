package com.example.homehive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.TestComponent

@Composable
fun OvenScreen(navController: NavController, innerPadding: PaddingValues?) {
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        Text("I AM INSIDE AN OVEN")
    }
}