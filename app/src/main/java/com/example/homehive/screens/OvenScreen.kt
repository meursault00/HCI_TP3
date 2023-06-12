package com.example.homehive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController

@Composable
fun OvenScreen(navController: NavController, innerPadding: PaddingValues?) {
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        Text("I AM INSIDE AN OVEN")
    }
}