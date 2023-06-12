package com.example.homehive.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.boxes.RoutineBox

@Composable
fun RoutinesScreen(
    navController: NavController,
    innerPadding: PaddingValues?
) {
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                RoutineBox(onClick = {
                    // Handle OvenBox click event here
                })
            }
            item {
                RoutineBox(onClick = {
                    // Handle OvenBox click event here
                })
            }
            item {
                RoutineBox(onClick = {
                    // Handle OvenBox click event here
                })
            }
            item {
                RoutineBox(onClick = {
                    // Handle OvenBox click event here
                })
            }
            item {
                RoutineBox(onClick = {
                    // Handle OvenBox click event here
                })
            }
        }
    }
}

