package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.sendCustomNotification
import com.example.homehive.viewmodels.OvenVM


@Composable
fun OvenBox(onClick: () -> Unit, viewModel : OvenVM = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current;

    var isOn = remember { mutableStateOf(false) }

    var isOpen = remember { mutableStateOf(false) }

    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 415.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(height)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)

                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFEFE5C5)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center

            ) {
                Image(
                    painter = painterResource(id = R.drawable.fuego),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                )
                Text(
                    text = "Oven",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF114225),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )

                if(!isOpen.value){
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .align(Alignment.Center)
                    ){
                        Text(
                            text = "${uiState.ovenTemperature}ºC",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color(0xFFE3592B),
                            modifier = Modifier.padding(top = 16.dp)
                        )
                        Text(
                            text = if(isOn.value) "On" else "Off",
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF114225),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 20.dp)
                        )
                    }
                }

                Button(
                    onClick = { isOpen.value = !isOpen.value },
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
                        containerColor = if(isOpen.value)  Color(0xFFEFE5C5) else Color(0xB4EFE5C5)
                    ),
                ) {
                    Icon(
                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                        contentDescription = null,
                        tint =  Color(0xFFAFA586) ,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }

                if(isOpen.value){
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier
                            .align(Alignment.Center)
                    ){
                        Button(
                            onClick = { isOn.value = !isOn.value
                                        sendCustomNotification(context, "Oven", if(isOn.value) "Turned On" else "Turned Off")
                                      },
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 30.dp,
                                pressedElevation = 0.0.dp,
                            ),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEFE5C5),
                                contentColor = Color(0xFF1C6135)
                            ),
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text(text = if (isOn.value) "Turn Off" else "Turn On",
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFFAFA586),

                                )
                        }

                        Text(
                            text = "${uiState.ovenTemperature}ºC",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color(0xFFE3592B),
                        )
                        Text(
                            text = "Grill Mode: " + uiState.grillMode,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF114225),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                        )

                        Text(
                            text = "Convection Mode: " + uiState.convectionMode,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF114225),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                        )

                        Text(
                            text = "Source Mode: " + uiState.heatMode,
                            fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF114225),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                    }
                }


                if (!isOn.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
                }
                else{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}