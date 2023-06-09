package com.example.homehive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApp2()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MyApp() {
    Scaffold(
        containerColor = Color(0xFF203831),
        topBar = {
            TopAppBar(

                colors = TopAppBarDefaults.smallTopAppBarColors(
                    containerColor = Color(0xFF203831) // Set the background color
                ),
                title = {
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .background(Color(0xFF294D42))
                            .shadow(30.dp)
                            .clip(MaterialTheme.shapes.medium),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { /* Handle menu button click */ },
                                modifier = Modifier.padding(end = 8.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.Menu,
                                    contentDescription = "Menu",
                                    tint = Color(0xFFEECC66)
                                )
                            }
                            Text(
                                text = "HomeHive",
                                fontSize = 30.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(0xFFEECC66)
                            )
                        }
                    }
                },
                actions = {
                    Box(
                    modifier = Modifier
                        .padding(4.dp)
                        .background(color = Color(0xFF497065), shape = CircleShape)
                        .clip(CircleShape),
                    contentAlignment = androidx.compose.ui.Alignment.Center
                ) {
                    IconButton(
                        onClick = {
                        }
                    ) {
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
                            .clip(CircleShape),
                        contentAlignment = androidx.compose.ui.Alignment.Center
                    ) {
                        IconButton(
                            onClick = {
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Star,
                                contentDescription = "Add",
                                tint = Color(0xFFAFC1BB)
                            )
                        }
                    }
                }

            )
        },
        content = { innerPadding ->
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun MyApp2() {
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
                    Text(
                        text = "HomeHive",
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEECC66)
                    )
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
                    IconButton(onClick = {}) {
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
                        .clip(CircleShape)
                        .shadow(60.dp),
                    contentAlignment = Alignment.Center
                ) {
                    IconButton(onClick = {}) {
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
            LazyColumn(
                contentPadding = innerPadding,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                val list = (0..75).map { it.toString() }
                items(count = list.size) {
                    Text(
                        text = list[it],
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                    )
                }
            }
        }
    )
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


