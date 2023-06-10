package com.example.homehive

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homehive.ui.theme.HomeHiveTheme
import com.example.homehive.ui.theme.gris


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeHiveTheme {
                MyAppNavHost(startDestination = "myApp2")
            }
        }
    }
}


@Composable
fun OvenBox(onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .height(200.dp)
                .width(600.dp)
                .padding(vertical = 15.dp, horizontal = 15.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFF4CF6D)
        ) {
            Text(
                text = "OVEN",
                style = MaterialTheme.typography.headlineMedium,
                color = Color.DarkGray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
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
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                //si descomento el scroll se rompe todo
//                    .verticalScroll(rememberScrollState())
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(vertical = 10.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    val list = (0..75).map { it.toString() }
                    items(count = list.size) {
                        OvenBox(onClick = {
                            // Handle OvenBox click event here
                            // For example, you can navigate to another screen or perform some action

                        })
                    }
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


