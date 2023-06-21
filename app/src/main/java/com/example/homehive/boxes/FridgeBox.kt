// fridge box

package com.example.homehive.boxes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.library.FavoritesArray
import com.example.homehive.Globals
import com.example.homehive.R
import com.example.homehive.saveList
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.isDarkTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FridgeBox(
    onClick: () -> Unit,
    fridgeVM : FridgeVM = viewModel()
) {

    val uiState by fridgeVM.uiState.collectAsState()
    val context = LocalContext.current;
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
    val isOpen = remember { mutableStateOf(false) }
    val currentMode = uiState.mode
    val auxTemperature  = remember { mutableStateOf(uiState.temperature)}
    val auxFreezerTemperature = remember { mutableStateOf(uiState.freezerTemperature)}
    var isFavorite = remember { mutableStateOf(FavoritesArray.array.contains(uiState.id)) }

    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 415.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    fridgeVM.conditionalRecomposition()

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize(),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            border = if(isDarkTheme.value) BorderStroke(1.dp, MaterialTheme.colorScheme.background) else null,

            modifier = Modifier
                .height(height)
                .width(200.dp)
                .clickable { isOpen.value = !isOpen.value },
            shadowElevation = 5.dp,

            shape = RoundedCornerShape(15.dp),
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.TopCenter
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fridge),
                    contentDescription = null,
                    contentScale = ContentScale.FillHeight,
                    modifier = Modifier
                        .fillMaxSize()
                )

                Column(verticalArrangement = Arrangement.Top){
                    Row(
                        horizontalArrangement = Arrangement.End,
                        modifier = Modifier.fillMaxWidth()
                            .height(30.dp)
                            .padding(top = 10.dp)
                    ){
                        IconButton(
                            onClick = {
                                if (FavoritesArray.array.contains(uiState.id)) {
                                    FavoritesArray.array.remove(uiState.id)
                                    isFavorite.value = false
                                    Log.d("favorite", "removing from fav")
                                    saveList(sharedPrefs, FavoritesArray.array, "FavoritesList" )
                                } else {
                                    FavoritesArray.array.add(uiState.id)
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
                            text = uiState.name,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineMedium,
                            color = MaterialTheme.colorScheme.onTertiary,
                        )
                    }
                }

                if(!isOpen.value){
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text = "${uiState.freezerTemperature}°C",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyLarge,
                            color = Color(0xFF4BACC4),
                            modifier = Modifier.padding(top = 15.dp)
                        )
                        Text(
                            text = "${uiState.temperature}ºC",
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineLarge,
                            color = Color(0xFF216E81),
                            modifier = Modifier.align(Alignment.CenterHorizontally)
                        )
                        Text(
                            text = currentMode,
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF216E81),

                            )
                    }
                }
                else{
                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, top = 5.dp, end = 10.dp)
                            .align(Alignment.Center)
                    ) {
                        Text(
                            text =  stringResource(id = R.string.fridge_at ) + " ${auxTemperature.value}ºC",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                        Slider(
                            value = auxTemperature.value.toFloat(),
                            onValueChange = { newFridgeTemp ->
                                auxTemperature.value = newFridgeTemp.toInt()
                            },
                            onValueChangeFinished = {
                                fridgeVM.setFridgeTemperature(auxTemperature.value)
                            },
                            valueRange = 2f..8f,

                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.secondary,
                                activeTrackColor = Color(0xBE296874),
                                inactiveTrackColor = Color(0xFFEDF5F2).copy(alpha = 0.3f),
                            ),
                            modifier = Modifier
                        )
                        Text(
                            text =  stringResource(id = R.string.freezer_at) + " ${auxFreezerTemperature.value}ºC",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                        Slider(
                            value = auxFreezerTemperature.value.toFloat(),
                            onValueChange = { newFreezerTemp ->
                                auxFreezerTemperature.value = newFreezerTemp.toInt()
                            },
                            valueRange = -20f..-8f,
                            onValueChangeFinished = {
                                fridgeVM.setFreezerTemperature(auxFreezerTemperature.value)
                            },
                            colors = SliderDefaults.colors(
                                thumbColor = MaterialTheme.colorScheme.secondary,
                                activeTrackColor = Color(0x9EEDF3F1),
                                inactiveTrackColor = Color(0xFF2E4957).copy(alpha = 0.3f),
                            ),
                            modifier = Modifier
                        )

                        // MODE BUTTONS
                        Text(
                            text = stringResource(id = R.string.mode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp)
                        )
                        Button(
                            onClick = { fridgeVM.setMode("default") },
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
                                containerColor = if ( currentMode == "default" ) MaterialTheme.colorScheme.secondary else Color(0x54F3F3F0)
                            ),
                        ) {
                            Text(
                                text = stringResource(id = R.string.default_s),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (currentMode == "default") MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                            )
                        }
                        Button(
                            onClick = { fridgeVM.setMode("party") },
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
                                containerColor = if (currentMode =="party") MaterialTheme.colorScheme.secondary else Color(0x54F3F3F0)
                            ),
                        ) {
                            Text(
                                text = stringResource(id = R.string.party),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (currentMode == "party") MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiary,
                                modifier = Modifier
                            )
                        }
                        Button(
                            onClick = { fridgeVM.setMode("vacation") },
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
                                containerColor = if (currentMode == "vacation") MaterialTheme.colorScheme.secondary else Color(0x54F3F3F0)
                            ),
                        ) {
                            Text(
                                text = stringResource(id = R.string.vacation),
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (currentMode == "vacation") MaterialTheme.colorScheme.onTertiaryContainer else MaterialTheme.colorScheme.onTertiary,
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
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Icon(
                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                        contentDescription = null,
                        tint =  MaterialTheme.colorScheme.background ,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }


            }
        }
    }
}

