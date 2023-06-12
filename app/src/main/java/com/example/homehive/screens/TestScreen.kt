package com.example.homehive.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.TestComponent

@Composable
fun TestScreen(navController: NavController, innerPadding: PaddingValues?) {
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        Text(text = "Danger : Testing Zone")
        TestComponent()
    }
}