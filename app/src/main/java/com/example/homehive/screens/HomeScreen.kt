package com.example.homehive.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.boxes.BlindsBox
import com.example.homehive.boxes.FridgeBox
import com.example.homehive.boxes.OvenBox
import com.example.homehive.boxes.SpeakerBox
import com.example.homehive.boxes.TapBox

@Composable
fun HomeScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .padding(top = 70.dp)
    ) {
        LazyVerticalGrid(
            columns = GridCells.Adaptive(150.dp),
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            item {
                OvenBox(onClick = {
                    navController.navigate("devices/oven/1234")
                })
            }
            item {
                FridgeBox(onClick = {
                    navController.navigate("devices/fridge/1234")
                })
            }
            item {
                TapBox(onClick = {
                    navController.navigate("devices/tap/1234")
                })
            }
            item {
                BlindsBox(onClick = {
                    navController.navigate("devices/blinds/1234")
                })
            }
            item {
                SpeakerBox(onClick = {
                    navController.navigate("devices/speaker/1234")
                })
            }
        }
    }
}