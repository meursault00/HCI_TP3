package com.example.homehive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.boxes.BlindsBox
import com.example.homehive.boxes.FridgeBox
import com.example.homehive.boxes.OvenBox
import com.example.homehive.boxes.SpeakerBox
import com.example.homehive.boxes.TapBox

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(navController: NavController, innerPadding: PaddingValues?) {
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(150.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                OvenBox(onClick = {
                    navController.navigate("devices/oven/1234")
                })
            }
            item {
                FridgeBox(onClick = {})
            }
            item {
                TapBox(onClick = {})
            }
            item {
                BlindsBox(onClick = {})
            }
            item {
                SpeakerBox(onClick = {
                    navController.navigate("devices/speaker/1234")
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
