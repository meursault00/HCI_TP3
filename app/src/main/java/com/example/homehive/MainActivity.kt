package com.example.homehive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homehive.ui.theme.HomeHiveTheme
import androidx.navigation.NavController


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        createNotificationChannel(this)
        super.onCreate(savedInstanceState)
        setContent {
            HomeHiveTheme {
                NavHost(startDestination = "home")
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavController,
    content: @Composable (navController : NavController,  innerPadding : PaddingValues?) -> Unit
) {
    Scaffold(
        containerColor = Color(0xFF203831),
        topBar = {
            Box(
                modifier = Modifier
                    .padding(top = 22.dp, start = 20.dp)
                    .background(
                        color = Color(0xFF294D42),
                        shape = RoundedCornerShape(32.dp)
                    )
                    .padding(
                        start = 8.dp,
                        top = 0.dp,
                        end = 32.dp,
                        bottom = 4.dp
                    )
                    .shadow(20.dp),
                contentAlignment = Alignment.CenterStart,
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    IconButton(
                        onClick = { /* Handle menu button click */ },
                        modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Menu,
                            contentDescription = "Menu",
                            tint = Color(0xFFEECC66)
                        )
                    }
                    Box(
                        modifier = Modifier.clickable { navController.navigate("home") }
                    ) {
                        Text(
                            text = "HomeHive",
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(0xFFEECC66)
                        )
                    }
                }
            }
            Row(
                modifier = Modifier
                    .padding(top = 23.dp, end = 20.dp)
                    .fillMaxWidth()
                    .shadow(90.dp),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(color = Color(0xFF497065), shape = CircleShape)
                        .clip(CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {
                         navController.navigate("test")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Add,
                            contentDescription = "Add",
                            tint = Color(0xFFAFC1BB)
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
                    IconButton(onClick = {
                        navController.navigate("routines")
                    }) {
                        Icon(
                            imageVector = Icons.Filled.Star,
                            contentDescription = "Star",
                            tint = Color(0xFFAFC1BB)
                        )
                    }
                }
            }
        },
        content = { innerPadding ->
            content(navController = navController, innerPadding = innerPadding)
        }
    )
}





