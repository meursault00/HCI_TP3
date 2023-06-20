package com.example.homehive.boxes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.Globals
import com.example.homehive.R
import com.example.homehive.library.AnimatedTextOverflow
import com.example.homehive.library.FavoritesArray
import com.example.homehive.saveList
import com.example.homehive.viewmodels.SpeakerVM
import com.example.homehive.viewmodels.isDarkTheme


@Composable
fun SpeakerBox(
    onClick: () -> Unit,
    speakerVM : SpeakerVM = viewModel()
) {
    val speakerState by speakerVM.uiState.collectAsState()
    val context = LocalContext.current;
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    var isFavorite = remember { mutableStateOf(FavoritesArray.array.contains(speakerState.id)) }

    speakerVM.conditionalRecomposition()


    LaunchedEffect(Unit) {
        speakerVM.checkPolling()
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
            shadowElevation = 5.dp,
            modifier = Modifier
                .height(200.dp)

                .width(200.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = if(!isDarkTheme.value) R.drawable.spot else R.drawable.spotdark),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 16.dp)
                )

                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter){
                    Column(verticalArrangement = Arrangement.Top){
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier.fillMaxWidth()
                                .height(30.dp)
                                .padding(top = 10.dp)
                        ){
                            IconButton(
                                onClick = {
                                    if (FavoritesArray.array.contains(speakerState.id)) {
                                        FavoritesArray.array.remove(speakerState.id)
                                        isFavorite.value = false
                                        Log.d("favorite", "removing from fav")
                                        saveList(sharedPrefs, FavoritesArray.array, "FavoritesList" )
                                    } else {
                                        FavoritesArray.array.add(speakerState.id)
                                        isFavorite.value = true
                                        Log.d("favorite", "adding to fav")
                                        saveList(sharedPrefs, FavoritesArray.array, "FavoritesList" )
                                    }
                                },
                            ) {
                                Icon(
                                    painter = if (isFavorite.value) painterResource(id = R.drawable.heart_filled) else painterResource(id = R.drawable.heart_outline),
                                    contentDescription = null,
                                    tint = MaterialTheme.colorScheme.background,
                                    modifier = Modifier.size(30.dp)
                                )
                            }
                        }

                        Row(
                            horizontalArrangement = Arrangement.Center,
                            modifier = Modifier.fillMaxWidth()
                                .height(40.dp)
                        ){
                            Text(
                                text = speakerState.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }
                }

                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .align(Alignment.Center),
                ) {
                    AnimatedTextOverflow(
                        text = speakerState.song.title?: "Unavailable",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.padding(top = 15.dp)
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically, // Center vertically
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        FloatingActionButton(
                            onClick = {
                                if (speakerVM.uiState.value.status == "playing") {
                                    speakerVM.previousSong()
                                } },
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(16.dp),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(30.dp) // Adjust the size as desired
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.previous),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        FloatingActionButton(
                            onClick = {
                                when(speakerState.status) {
                                    "stopped" -> speakerVM.play()
                                    "paused" -> speakerVM.resume()
                                    else -> speakerVM.pause()
                                }
                            } ,
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(16.dp),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(40.dp), // Adjust the size as desired
                        ) {
                            Icon(
                                painter = painterResource(id = if(speakerState.status == "paused" || speakerState.status == "stopped") R.drawable.play else R.drawable.pause),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        FloatingActionButton(
                            onClick = {
                                if (speakerVM.uiState.value.status == "playing") {
                                    speakerVM.nextSong()
                            } },
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(16.dp),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(30.dp) // Adjust the size as desired
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.next),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }

                }

                Button(
                    onClick = onClick,
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    shape = RoundedCornerShape(topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp),
                    modifier = Modifier
                        .height(45.dp)
                        .width(200.dp)
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.music) ,
                        contentDescription = null,
                        tint =  MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(20.dp)
                    )

                }

            }
        }
    }
}