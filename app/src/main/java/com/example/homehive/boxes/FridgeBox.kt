package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.homehive.R


enum class FridgeMode {
    NORMAL,
    VACATION,
    PARTY,
}
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgeBox(onClick: () -> Unit) {
    var isOpen = remember { mutableStateOf(false) };

    var fridgeTemp = remember { mutableStateOf(2) };

    var fridgeMode = remember { mutableStateOf(FridgeMode.NORMAL) };
    var freezerTemp = remember { mutableStateOf(-8) };


    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 415.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )
    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .height(height)
                .width(200.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFF4CF6D)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fridge),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Text(
                    text = "Fridge",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF114225),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )
                if(!isOpen.value){
                    Text(
                        text = "${freezerTemp.value}°C",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.bodyLarge,
                        color = Color(0xFF4BACC4),
                        modifier = Modifier
                            .padding(start = 10.dp, bottom = 50.dp)
                            .align(Alignment.Center)
                    )
                    Text(
                        text = "${fridgeTemp.value}ºC",
                        fontWeight = FontWeight.Bold,
                        style = MaterialTheme.typography.headlineLarge,
                        color = Color(0xFF216E81),
                        modifier = Modifier
                            .align(Alignment.Center)
                    )
                }
                else{
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = "Fridge: ${fridgeTemp.value}ºC",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                        Slider(
                            value = fridgeTemp.value.toFloat(),
                            onValueChange = { newFridgeTemp ->
                                setFridgeTemp(
                                    newFridgeTemp.toInt(),
                                    fridgeTemp
                                )
                            },
                            valueRange = 2f..8f,

                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFEFE5C5),
                                activeTrackColor = Color(0xBE296874),
                                inactiveTrackColor = Color(0xFFEDF5F2).copy(alpha = 0.3f),
                            ),
                            modifier = Modifier
                        )
                        Text(
                            text = "Fridge: ${freezerTemp.value}ºC",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                        Slider(
                            value = freezerTemp.value.toFloat(),
                            onValueChange = { newFreezerTemp ->
                                setFreezerTemp(
                                    newFreezerTemp.toInt(),
                                    freezerTemp
                                )
                            },
                            valueRange = -20f..-8f,

                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFFEFE5C5),
                                activeTrackColor = Color(0x9EEDF3F1),
                                inactiveTrackColor = Color(0xFF2E4957).copy(alpha = 0.3f),
                            ),
                            modifier = Modifier
                        )

                        // MODE BUTTONS
                        Text(
                            text = "Mode",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Button(
                            onClick = { setFridgeMode(FridgeMode.NORMAL, fridgeMode) },
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 30.dp,
                                pressedElevation = 0.0.dp,
                            ),
                            shape = RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            ),
                            modifier = Modifier
                                .height(45.dp)
                                .width(200.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (fridgeMode.value == FridgeMode.NORMAL) Color(
                                    0xFFEFE5C5
                                ) else Color(0x54F3F3F0)
                            ),
                        ) {
                            Text(
                                text = "Normal",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF2B4E5C),
                                modifier = Modifier
                            )
                        }
                        Button(
                            onClick = { setFridgeMode(FridgeMode.VACATION, fridgeMode) },
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 30.dp,
                                pressedElevation = 0.0.dp,
                            ),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            ),
                            modifier = Modifier
                                .height(45.dp)
                                .width(200.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (fridgeMode.value == FridgeMode.VACATION) Color(
                                    0xFFEFE5C5
                                ) else Color(0x54F3F3F0)
                            ),
                        ) {
                            Text(
                                text = "Vacation",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF2B4E5C),
                                modifier = Modifier
                            )
                        }
                        Button(
                            onClick = { setFridgeMode(FridgeMode.PARTY, fridgeMode) },
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 30.dp,
                                pressedElevation = 0.0.dp,
                            ),
                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 0.dp,
                                bottomStart = 15.dp,
                                bottomEnd = 15.dp
                            ),
                            modifier = Modifier
                                .height(45.dp)
                                .width(200.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (fridgeMode.value == FridgeMode.PARTY) Color(
                                    0xFFEFE5C5
                                ) else Color(0x54F3F3F0)
                            ),
                        ) {
                            Text(
                                text = "Party",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF2B4E5C),
                                modifier = Modifier
                            )
                        }
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


            }
        }
    }
}

private fun setFridgeTemp(newFridgeTemp: Int, fridgeTemp: MutableState<Int> ) {
    fridgeTemp.value = newFridgeTemp
}

private fun setFreezerTemp(newFreezerTemp: Int, freezerTemp: MutableState<Int> ) {
    freezerTemp.value = newFreezerTemp
}

private fun setFridgeMode(newFridgeMode: FridgeMode, fridgeMode: MutableState<FridgeMode> ) {
    fridgeMode.value = newFridgeMode
}