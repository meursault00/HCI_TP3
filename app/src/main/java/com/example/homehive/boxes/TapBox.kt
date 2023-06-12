package com.example.homehive.boxes

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.animateDpAsState

import androidx.compose.animation.core.tween
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.homehive.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TapBox(onClick: () -> Unit) {

    var isOpen = remember { mutableStateOf(false) };
    var isOn = remember { mutableStateOf(true) };
    var tapDispensingValue = remember { mutableStateOf("") };

    val Height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )
    val Width: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 400.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(Height)
            .width(Height)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier

                .padding(vertical = 15.dp, horizontal = 15.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFA0CCCF)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.tap),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                )
                Text(
                    text = "Tap",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF114225),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )
                if(!isOpen.value){
                    Text(
                        text = if(isOn.value) "On" else "Off",
                        fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF114225),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(16.dp)
                            .align(Alignment.Center)
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
                        containerColor = Color(0xB4EFE5C5)
                    ),
                ) {
                    Icon(
                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                        contentDescription = null,
                        tint = Color(0xFFAFA586),
                        modifier = Modifier
                            .size(60.dp)
                    )
                }

                if (!isOn.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
                }
                if(isOpen.value){
                    Button(
                        onClick = { toggleTap(isOn) },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 30.dp,
                            pressedElevation = 0.0.dp,
                        ),
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .align(Alignment.TopCenter), // Align the button to the end (top end of the Box)
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEFE5C5),
                            contentColor = Color(0xFF114223)
                        )
                    ) {
                        Text(text = if (isOn.value) "Turn On" else "Turn Off",
                            style = MaterialTheme.typography.bodySmall,
                            color = Color(0xFFAFA586),

                        )
                    }
                    TextField(
                        value = tapDispensingValue.value,
                        onValueChange = { tapDispensingValue.value = it },
                        label = { Text(text = "Dispensing Value") },
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Number,
                            imeAction = ImeAction.Done
                        ),
                        keyboardActions = KeyboardActions(
                            onDone = { updateTapDispensingValue(tapDispensingValue.value) }
                        ),
                        modifier = Modifier
                            .padding(top = 60.dp)
                            .align(Alignment.TopCenter), // Align the button to the end (top end of the Box)

                    )

                }

            }
        }
    }
}

private fun toggleTap(isOn: MutableState<Boolean>){
    isOn.value = !isOn.value;
}

private fun updateTapDispensingValue(tapDispensingValue: String){

    println(tapDispensingValue)
}