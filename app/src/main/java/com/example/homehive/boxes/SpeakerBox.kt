package com.example.homehive.boxes

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.Globals
import com.example.homehive.R
import com.example.homehive.viewmodels.SettingsVM
import com.example.homehive.viewmodels.SpeakerVM
import com.example.homehive.viewmodels.isDarkTheme


@Composable
fun SpeakerBox(
    onClick: () -> Unit,
    speakerVM : SpeakerVM = viewModel()
) {

    if ( Globals.updates > 0 ){
        speakerVM.sync()
        Globals.updates--
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.secondary,
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
                Text(
                    text = stringResource(id = R.string.speaker),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .align(Alignment.Center),
                ) {
                    Text(
                        text = stringResource(id = R.string.current_song),
                        color = MaterialTheme.colorScheme.onPrimary,
                    )
                    Row(
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically, // Center vertically
                        modifier = Modifier
                            .fillMaxWidth()
                    ){
                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
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
                            onClick = { /*TODO*/ },
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
                            containerColor = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier.size(40.dp) // Adjust the size as desired
                        ) {
                            Icon(
                                painter = painterResource(id = R.drawable.play),
                                contentDescription = null,
                                tint = MaterialTheme.colorScheme.onSurface,
                                modifier = Modifier.size(20.dp)
                            )
                        }
                        FloatingActionButton(
                            onClick = { /*TODO*/ },
                            shape = CircleShape,
                            elevation = FloatingActionButtonDefaults.elevation(0.dp, 0.dp),
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