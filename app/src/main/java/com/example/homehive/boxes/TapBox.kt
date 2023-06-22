package com.example.homehive.boxes

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.infiniteRepeatable

import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.withFrameNanos
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.Globals
import com.example.homehive.R
import com.example.homehive.library.FavoritesArray
import com.example.homehive.library.sendCustomNotification
import com.example.homehive.saveList
import com.example.homehive.viewmodels.TapVM
import com.example.homehive.viewmodels.isDarkTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TapBox(onClick: () -> Unit, tapVM : TapVM = viewModel()) {

    val tapState by tapVM.uiState.collectAsState()

    val context = LocalContext.current
    val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    val isOpen = remember { mutableStateOf(false) }
    val isOn  = remember { mutableStateOf(tapState.status == "opened") }
//    var isOn = remember { mutableStateOf(false) }
    val isDispensing = remember { mutableStateOf(false) }
    val dispenseValue = remember { mutableStateOf("10") }
    val dispenseValueError = remember { mutableStateOf("") }
    val dispenseValueHasError = remember { mutableStateOf(false) }

    val dispenseOptions = setOf("mL", "cL", "dL", "L")
    val dispenseUnit = remember { mutableStateOf("dL") }
    val dispenseUnitError = remember { mutableStateOf("") }
    val dispenseUnitHasError = remember { mutableStateOf(false) }
    var isFavorite = remember { mutableStateOf(FavoritesArray.array.contains(tapState.id)) }

    val isDispensingNormal = remember { mutableStateOf(false) }


    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 415.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    tapVM.conditionalRecomposition()

    LaunchedEffect(isOpen.value) {
        if(isOpen.value){
            tapVM.checkPolling()
        }
    }

    Box(
        modifier = Modifier
            .padding(10.dp)
            .fillMaxSize()
            .height(height),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            shadowElevation = 5.dp,
            border = if(isDarkTheme.value) BorderStroke(1.dp, MaterialTheme.colorScheme.background) else null,
            modifier = Modifier
                .width(200.dp)
                .clickable { isOpen.value = !isOpen.value },
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.onBackground,
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
                if(tapState.status == "opened" ){
                    Log.d("dispensing", "dispensing")
                    RippleAnimation(
                        circleColor = Color(0x23C3F6FF),
                        animationDelay = 2500,
                    )
                }
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
                                    if (FavoritesArray.array.contains(tapState.id)) {
                                        FavoritesArray.array.remove(tapState.id)
                                        isFavorite.value = false
                                        Log.d("favorite", "removing from fav")
                                        saveList(sharedPrefs, FavoritesArray.array, "FavoritesList" )
                                    } else {
                                        FavoritesArray.array.add(tapState.id)
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
                                text = tapState.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineMedium,
                                color = MaterialTheme.colorScheme.onPrimary,
                            )
                        }
                    }
                }
                if(isDispensing.value && !isOpen.value && tapState.status == "opened"){
                    Text(
                        text = stringResource(id = R.string.dispensing)+ " ${ dispenseValue.value } ${ dispenseUnit.value }",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .padding(top = 60.dp)
                    )
                }

                if (tapState.status == "closed") {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
                }

                Button(
                    onClick = { isOpen.value = !isOpen.value },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 12.dp,
                        pressedElevation = 12.0.dp,
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


                if(isDispensing.value && isOpen.value && tapState.status == "opened"){

                    Column(
                        modifier = Modifier
                            .align(Alignment.Center)
                    ) {

                        Text(
                            text = stringResource(id = R.string.dispensing)+ " ${ dispenseValue.value } ${ dispenseUnit.value }",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier
                                .padding(top = 90.dp)
                                .align(Alignment.CenterHorizontally)
                        )
                    }
                }else if(isOpen.value && tapState.status == "opened"){
                    Text(
                        text = stringResource(id = R.string.dispensing),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onTertiary,
                        modifier = Modifier
                            .padding(top =90.dp)
                    )
                }


                Button(
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.background),

                    onClick = { if(isOn.value) tapVM.setClose() else tapVM.setOpen()

                        isDispensingNormal.value = !isDispensingNormal.value
                        isOn.value = !isOn.value
                        isDispensing.value = false
                        dispenseValue.value = "10"
                        dispenseUnit.value = "dL"
                        sendCustomNotification(context, "Tap",  context.getString(R.string.tap) + " " + if(isOn.value) context.getString(R.string.opened) else context.getString(R.string.closed))
                    },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .align(Alignment.TopCenter), // Align the button to the end (top end of the Box)
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(isOn.value) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                    )
                ) {
                    Text(text = if (isOn.value) stringResource(id = R.string.turn_off) else stringResource(id = R.string.turn_on),
                        style = MaterialTheme.typography.bodySmall,
                        color = if(isOn.value) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary,

                        )
                }

                if(isOpen.value){



                    if(!isOn.value){
                        // FORM
                        OutlinedTextField(

                            value = dispenseValue.value,
                            isError = dispenseValueHasError.value,
                            onValueChange = { newValue ->
                                // Ensure the value falls within the range of 1 to 100

                                val sanitizedValue = newValue.takeIf { it.isBlank() || it.toIntOrNull() in 1..100 } ?: dispenseValue.value
                                dispenseValue.value = sanitizedValue
                                dispenseValueHasError.value = false

                            },
                            shape = RoundedCornerShape(15.dp),
                            colors = TextFieldDefaults.outlinedTextFieldColors(
                                containerColor = Color(0x4DEFE5C5),
                                focusedBorderColor = Color(0x00D3DEE0),
                                unfocusedBorderColor = Color(0x00D3DEE0),
                                cursorColor = Color(0xFFD3DEE0),
                                textColor = Color(0xFFD3DEE0),
                            ),
                            label = { Text(text = stringResource(id = R.string.value), color = Color(0xFFD3DEE0)) },
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                // hide keyboard when the user presses the Done button

                            ),
                            modifier = Modifier
                                .padding(bottom = 80.dp)
                                .height(80.dp)
                                .width(100.dp)
                                .align(Alignment.Center), // Align the button to the end (top end of the Box)
                        )
                        if(dispenseValueHasError.value){
                            Text(
                                text = dispenseValueError.value,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Red,
                                modifier = Modifier
                                    .padding(top = 20.dp)
                                    .align(Alignment.Center)
                            )
                        }

                        Row( // ROW DE ELECCION DE LA UNIDAD
                            modifier = Modifier
                                .padding(top = 100.dp)
                                .align(Alignment.Center)
                        ) {
                            dispenseOptions.forEach {
                                Column (
                                    modifier = Modifier
                                        .padding(horizontal = 5.dp)
                                ) {

                                    RadioButton(
                                        modifier = Modifier
                                            .size(20.dp)
                                            .padding(5.dp),
                                        selected = dispenseUnit.value == it,
                                        onClick = { dispenseUnit.value = it },
                                        colors = RadioButtonDefaults.colors(
                                            selectedColor = Color(0xFFD3DEE0),
                                            unselectedColor = Color(0xFFAFA997)
                                        )
                                    )
                                    Text(
                                        text = it,
                                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                        style = MaterialTheme.typography.bodySmall,
                                        color = Color(0xFFD3DEE0),
                                        fontWeight = FontWeight.Bold,
                                    )
                                    }
                                }
                            }
                        if(dispenseUnitHasError.value){
                            Text(
                                text = dispenseUnitError.value,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color.Red,
                                modifier = Modifier
                                    .padding(top = 150.dp)
                                    .align(Alignment.Center)
                            )
                        }
                        // VALIDATION

                        Button(
                            onClick = {
                                  when{
                                      dispenseValue.value.isEmpty() -> {
                                            dispenseValueError.value = context.getString(R.string.value_is_required)
                                            dispenseValueHasError.value = true
                                      }
                                        dispenseUnit.value.isEmpty() -> {
                                            dispenseUnitError.value = context.getString(R.string.unit_is_required)
                                            dispenseUnitHasError.value = true
                                        }
                                        else -> {
                                            dispenseUnitHasError.value = false
                                            dispenseValueHasError.value = false
                                            tapVM.dispense(dispenseValue.value.toInt(), dispenseUnit.value)
                                            isOn.value = true
                                            isDispensing.value = true
                                        }
                                  }
                            },
                            elevation = ButtonDefaults.buttonElevation(
                                defaultElevation = 30.dp,
                                pressedElevation = 0.0.dp,
                            ),
                            modifier = Modifier
                                .padding(top = 230.dp)
                                .align(Alignment.Center), // Align the button to the end (top end of the Box)
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondary,

                            )
                        ) {
                            Text(text = stringResource(id = R.string.dispense),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.background,

                                )
                        }
                    }

                }

            }
        }
    }
}


@Composable
fun RippleAnimation(
    circleColor: Color = Color.Magenta,
    animationDelay: Int = 1500
) {

    // 3 circles
    val circles = listOf(
        remember {
            Animatable(initialValue = 0f)
        },
        remember {
            Animatable(initialValue = 0f)
        },
        remember {
            Animatable(initialValue = 0f)
        }
    )

    circles.forEachIndexed { index, animatable ->
        LaunchedEffect(Unit) {
            // Use coroutine delay to sync animations
            // divide the animation delay by number of circles
            delay(timeMillis = (animationDelay / 3L) * (index + 1))

            animatable.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(
                        durationMillis = animationDelay,
                        easing = LinearEasing
                    ),
                    repeatMode = RepeatMode.Restart
                )
            )
        }
    }

    // outer circle
    Box(
        modifier = Modifier
            .size(size = 200.dp)
            .background(color = Color.Transparent)
    ) {
        // animating circles
        circles.forEachIndexed { index, animatable ->
            Box(
                modifier = Modifier
                    .scale(scale = animatable.value)
                    .size(size = 200.dp)
                    .clip(shape = CircleShape)
                    .background(
                        color = circleColor
                            .copy(alpha = (1 - animatable.value))
                    )
            ) {
            }
        }
    }
}

