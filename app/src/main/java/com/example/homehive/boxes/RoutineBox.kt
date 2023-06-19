package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.library.AnimatedTextOverflow
import com.example.homehive.network.routineModels.NetworkActions
import com.example.homehive.viewmodels.RoutineVM

@Composable
fun RoutineBox(
    routineVM : RoutineVM = viewModel(),
    isMinimalist : Boolean = false,
) {
    val uiState by routineVM.uiState.collectAsState()
    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 400.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    val routineActions: ArrayList<NetworkActions> = uiState.actions

    val actionExplanations: List<String> = routineActions.map { action ->
        formatActionString(action.device?.name, action.actionName ?: "", action.params)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(blindsHeight),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .clickable { isOpen.value = !isOpen.value },
            shape = RoundedCornerShape(15.dp),
            shadowElevation = 5.dp,

            color = MaterialTheme.colorScheme.secondary
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = uiState.name,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )
                Button( // CHECKEAR CONDICIONES DE ESTADO
                    onClick = { routineVM.executeRoutine(uiState.id) },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .align(Alignment.TopCenter), // Align the button to the end (top end of the Box)
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.onSecondary,

                        )
                ) {
                    Text(
                        stringResource(id = R.string.run),
                        color = MaterialTheme.colorScheme.secondary

                    )
                }
                Text(
                    text = "${actionExplanations.size} " + if(actionExplanations.size == 1) stringResource(id = R.string.action) else stringResource(id = R.string.actions),
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .align(Alignment.TopCenter)
                )

                if(!isMinimalist){
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
                            containerColor = MaterialTheme.colorScheme.onSecondary
                        ),
                    ) {
                        Icon(
                            painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                            contentDescription = "Arrow that opens up routine box showing its actions",
                            tint = MaterialTheme.colorScheme.secondary,
                            modifier = Modifier
                                .size(60.dp)
                        )
                    }
                }

                if(isOpen.value) {

                    Surface(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shadowElevation = 12.dp,
                        shape = RoundedCornerShape(15.dp),
                        modifier = if(isMinimalist) Modifier
                            .padding(10.dp)
                            .fillMaxSize()
                        else
                            Modifier
                                .alpha(0.9f)
                                .padding(top = 90.dp, start = 5.dp, end = 5.dp)
                                .height(170.dp)
                                .fillMaxWidth()
                    ){

                        RoutineList(
                            items = actionExplanations,
                            routineName = uiState.name
                        )

                    }

                }

            }
        }
    }
}


@Composable
fun RoutineList(items: List<String>, routineName: String = "Routine Name") {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .background(MaterialTheme.colorScheme.onPrimary)
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = routineName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items) { index, task ->
                val rowBGColor = if (index % 2 == 0) {
                    MaterialTheme.colorScheme.secondary // Even item background color
                } else {
                    MaterialTheme.colorScheme.onSecondary // Odd item background color
                }
                RTaskRow(task = task, bgColor = rowBGColor)
            }
        }
    }
}

@Composable
fun RTaskRow(task: String = "This is a task that probably occupies mutliple char spaces", bgColor: Color = MaterialTheme.colorScheme.secondary) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = bgColor,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .padding(start = 3.dp, end = 3.dp)
                    .weight(1f)
            ) {
                AnimatedTextOverflow(
                    text = task,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if(bgColor == MaterialTheme.colorScheme.onSecondary) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
                )

            }
        }

    }
}

@Composable
fun formatActionString(deviceName: String?, actionName: String, params: ArrayList<String>): String {
    val formattedString = StringBuilder()

    formattedString.append(deviceName ?: "Unknown device")
    formattedString.append(" " + stringResource(id = R.string.will)+ " " )
    formattedString.append(getActionString(actionName))

    if (params.isNotEmpty()) {
        if(getActionString(actionName) != "dispense") formattedString.append(" " + stringResource(id = R.string.to) + " ");
        formattedString.append(params.joinToString(" "))
    }

    return formattedString.toString()
}
@Composable
fun getActionString(actionName: String): String {
    return when (actionName) {
        "setVolume" -> "set volume"
        "play" -> "play"
        "stop" -> "stop"
        "pause" -> "pause"
        "resume" -> "resume"
        "nextSong" -> "play next song"
        "previousSong" -> "play previous song"
        "setGenre" -> "set genre"
        "getPlaylist" -> "get playlist"
        "turnOn" -> "turn on"
        "turnOff" -> "turn off"
        "setTemperature" -> "set temperature"
        "setHeat" -> "set heat"
        "setGrill" -> "set grill"
        "setConvection" -> "set convection"
        "open" -> "open"
        "close" -> "close"
        "setLevel" -> "set level"
        "setMode" -> "set mode"
        "setFreezerTemperature" -> "set freezer temperature"
        "dispense" -> "dispense"
        else -> "Unknown action"
    }
}