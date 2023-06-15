package com.example.homehive

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Star
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController

@Composable
fun AppBar(
    navController: NavController,
    onNavigationIconClick: () -> Unit
) {
    Row(Modifier.fillMaxWidth()){
        Box(
            modifier = Modifier
                .padding(top = 22.dp, start = 20.dp, end = 20.dp)
                .background(
                    color = Color(0xFF203831),
                    shape = RoundedCornerShape(32.dp)
                )
                    // SHADOW DE CHRIS
                .fillMaxWidth(),
            contentAlignment = Alignment.CenterStart,
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    IconButton(
                        onClick = onNavigationIconClick,
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Toggle Drawer",
                            tint = Color(0xFFEECC66)
                        )
                    }
                    Box(
                        modifier = Modifier.clickable { navController.navigate("home") }
                    ) {
                        androidx.compose.material3.Text(
                            text = "HomeHive",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEECC66)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(color = Color(0xFF497065), shape = CircleShape)
                        .shadow(60.dp)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    androidx.compose.material3.IconButton(onClick = {
                        navController.navigate("routines")
                    }) {
                        androidx.compose.material3.Icon(
                            imageVector = Icons.Filled.Check, // CAMBIAR ICONO
                            contentDescription = "Star",
                            tint = Color(0xFFAFC1BB)
                        )
                    }
                }
            }
        }
    }
}