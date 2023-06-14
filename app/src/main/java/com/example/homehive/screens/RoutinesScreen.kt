package com.example.homehive.screens

import android.graphics.Paint.Align
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.modifierLocalConsumer
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController
import com.example.homehive.boxes.RoutineBox
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun RoutinesScreen(
    navController: NavController,
    innerPadding: PaddingValues?
) {
    var popupControl by remember { mutableStateOf(false) }
    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
            verticalArrangement = Arrangement.spacedBy(5.dp)
        ) {
            //ciclar sobre lista de rutinas
            item {
//                RoutineBox(onClick = { navController.navigate("routines/${it.id}") })
                RoutineBox(onClick = { popupControl = true })
            }

            //sigo dentro del scope del for que itera sobre las routines
//            if(popupControl) {
//                Popup() {
//
//                }
//            }
        }
        if(popupControl) {
            Popup(
                alignment = Alignment.Center,
                onDismissRequest = { popupControl = false },
                offset = IntOffset(0, 500),
                properties = PopupProperties(
                    focusable = true,
                    dismissOnBackPress = true,
                    dismissOnClickOutside = true
                )
            ) {
                Box(
                    modifier = Modifier
                        .padding(innerPadding ?: PaddingValues())
                        .height(400.dp)
                        .width(270.dp)
                        .align(Alignment.Center)
                ) {
                    Surface(
                        modifier = Modifier
                            .height(400.dp)
                            .width(270.dp)
                            .align(Alignment.Center),
                        shape = RoundedCornerShape(10.dp),
                        color = Color.White.copy(alpha = 0.95f)
                    ) {
                        Text(
                            "Routine Name Actions",
                            Modifier.align(Alignment.TopCenter),
                            fontSize = 15.sp
                        )
                    }
                }
            }
        }
    }
}
