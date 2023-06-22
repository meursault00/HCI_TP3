package com.example.homehive.boxes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.SideEffect
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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.library.FavoritesArray
import com.example.homehive.Globals
import com.example.homehive.R
import com.example.homehive.UpdateMap
import com.example.homehive.saveList
import com.example.homehive.viewmodels.BlindsVM
import com.example.homehive.viewmodels.isDarkTheme


@Composable
fun BlindsBox(onClick: () -> Unit, blindsVM : BlindsVM = viewModel()) {

    val blindState by blindsVM.uiState.collectAsState();
    val context = LocalContext.current;
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    var isBlindOpen = remember { mutableStateOf(blindState.status == "opened")}
    var isBlindClosed = remember { mutableStateOf(blindState.status == "closed") }
    var isClosing = remember { mutableStateOf(blindState.status == "closing")}
    var state = remember { mutableStateOf(blindState.status) }


    var auxBlindsPosition = remember { mutableStateOf(blindState.level) }
    var isFavorite = remember { mutableStateOf(FavoritesArray.array.contains(blindState.id)) }

    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    SideEffect{
        blindsVM.conditionalRecomposition()
    }

    // Perform your logic here
    blindsVM.checkPolling()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .height(blindsHeight),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            border = if(isDarkTheme.value) BorderStroke(1.dp, MaterialTheme.colorScheme.background) else null,
            shadowElevation = 5.dp,
            modifier = Modifier
                .width(200.dp)
                .clickable { isOpen.value = !isOpen.value },

            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ) {

            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = if(!isDarkTheme.value) R.drawable.blinds else R.drawable.blindsdark),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .offset { IntOffset(x = 100 - blindState.position  , y = 0) }
                )

                Box(modifier = Modifier
                    .fillMaxSize(),
                    contentAlignment = Alignment.TopCenter){
                    Column(verticalArrangement = Arrangement.Top){
                        Row(
                            horizontalArrangement = Arrangement.End,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(30.dp)
                                .padding(top = 10.dp)
                        ){
                            IconButton(
                                onClick = {
                                    if (FavoritesArray.array.contains(blindState.id)) {
                                        FavoritesArray.array.remove(blindState.id)
                                        isFavorite.value = false
                                        Log.d("favorite", "removing from fav")
                                        saveList(sharedPrefs, FavoritesArray.array, "FavoritesList" )
                                    } else {
                                        FavoritesArray.array.add(blindState.id)
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
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                        ){
                            Text(
                                text = blindState.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                                overflow = TextOverflow.Ellipsis,
                                maxLines = 1,
                                modifier = Modifier.padding(start = 5.dp, end = 5.dp)
                            )
                        }
                    }
                }



                if (blindState.position == 100) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
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
                        tint =  MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .padding(top = 20.dp)
                        .fillMaxWidth()
                ){
                    Button( // CHECKEAR CONDICIONES DE ESTADO
                        onClick = {

                            blindsVM.toggleBlinds()

                                  },
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.background),
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 30.dp,
                            pressedElevation = 0.0.dp,
                        ),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(!(blindState.status == "closing" || blindState.status == "closed")) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                        )
                    ) {
                        Text(
                            text = if(state.value == "opening") "STOP" else "OPEN",
                            color = if(state.value == "opening") MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary

                        )
                    }
                    Text(
                        text = stringResource(id = R.string.current)+" ${blindState.position}" + " ${blindState.status}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                    )
                    if(isOpen.value){
                        Text(
                            text = stringResource(id = R.string.max_position)+" ${auxBlindsPosition.value}",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                        )
                    }
                }

                if(isOpen.value) {

                    Slider(
                        enabled = blindState.status == "opened" || blindState.status == "closed",
                        colors = SliderDefaults.colors(
                            thumbColor = MaterialTheme.colorScheme.onPrimary,
                            activeTrackColor = MaterialTheme.colorScheme.onPrimary,
                            inactiveTrackColor = MaterialTheme.colorScheme.background
                        ),
                        value = auxBlindsPosition.value.toFloat(),
                        onValueChange = { newPosition ->
                            auxBlindsPosition.value = newPosition.toInt()
                        },
                        onValueChangeFinished = {
                            blindsVM.setPosition(auxBlindsPosition.value)
                        },
                        valueRange = 0f..100f,
                        modifier = Modifier
                            .padding(bottom = 50.dp, start = 10.dp, end = 10.dp)
                            .align(Alignment.BottomCenter),
                    )
                }
            }
        }
    }
}
