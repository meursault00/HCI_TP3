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
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homehive.R

@Composable
fun RoutineBox() {
    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(blindsHeight),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp),
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.secondary
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Routine",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF2c432d),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )
                Button( // CHECKEAR CONDICIONES DE ESTADO
                    onClick = {  },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    modifier = Modifier
                        .padding(top = 70.dp)
                        .align(Alignment.TopCenter), // Align the button to the end (top end of the Box)
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF795454),

                        )
                ) {
                    Text(
                        "RUN",
                        color = MaterialTheme.colorScheme.secondary

                    )
                }
                Text(
                    text = "3 Actions",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(top = 120.dp)
                        .align(Alignment.TopCenter)
                )

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
                        containerColor = Color(0xFF795454)
                    ),
                ) {
                    Icon(
                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                        contentDescription = "Arrow that opens up routine box showing its actions",
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }
                if(isOpen.value) {
                    //iterar sobre las actions de la rutina
                    Column(
                        modifier = Modifier
                            .padding(top = 70.dp)
                    ) {
                        Text(
                            text = "- Turn on lamp",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF86888A),
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = "- Lock the sex dungeon door",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF86888A),
                            textAlign = TextAlign.Start,
                        )
                        Text(
                            text = "- Draw the curtains to 30%",
                            style = MaterialTheme.typography.bodyMedium,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = Color(0xFF86888A),
                            textAlign = TextAlign.Start,
                        )
                    }

                }

            }
        }
    }
}
//@Composable
//fun RoutineBox(onClick: () -> Unit) {
//    var isOpen = remember { mutableStateOf(false) }
//
//    val blindsHeight: Dp by animateDpAsState(
//        targetValue = if (isOpen.value) 300.dp else 200.dp,
//        animationSpec = tween(durationMillis = 100)
//    )
//    Box(
//        modifier = Modifier
//            .fillMaxSize()
//            .clickable(onClick = onClick),
//        contentAlignment = Alignment.Center,
//    ) {
//        Surface(
//            modifier = Modifier
//                .height(200.dp)
//                .width(200.dp)
//                .clickable(onClick = onClick),
//            shape = RoundedCornerShape(15.dp),
//            color = MaterialTheme.colorScheme.secondary
//        ) {
//            Box(
//                modifier = Modifier.fillMaxSize(),
//                contentAlignment = Alignment.Center
//            ) {
//                Text(
//                    text = "RoutineName",
//                    fontWeight = FontWeight.Bold,
//                    style = MaterialTheme.typography.headlineMedium,
//                    color = Color(0xFF114225),
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .align(Alignment.TopCenter)
//                )
//                Button(
//                    onClick = { /* RUN THE ROUTINE */ },
//                    elevation = ButtonDefaults.buttonElevation(
//                        defaultElevation = 30.dp,
//                        pressedElevation = 0.0.dp,
//                    ),
//                    modifier = Modifier
//                        .padding(16.dp)
//                        .align(Alignment.BottomEnd)
//                        .background(MaterialTheme.colorScheme.secondary),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = if (true) MaterialTheme.colorScheme.secondary else Color(0xFFEEE5C9)),
//
//                ) {
//                    Text(
//                        text = "RUN",
//                        style = MaterialTheme.typography.bodyMedium,
//                        color = Color.DarkGray,
//                        textAlign = TextAlign.Center
//                    )
//                }
//                Button(
//                    onClick = { isOpen.value = !isOpen.value },
//                    elevation = ButtonDefaults.buttonElevation(
//                        defaultElevation = 30.dp,
//                        pressedElevation = 0.0.dp,
//                    ),
//                    shape = RoundedCornerShape(topStart = 15.dp,
//                        topEnd = 15.dp,
//                        bottomStart = 0.dp,
//                        bottomEnd = 0.dp),
//                    modifier = Modifier
//                        .height(45.dp)
//                        .width(200.dp)
//                        .align(Alignment.BottomCenter),
//                    colors = ButtonDefaults.buttonColors(
//                        containerColor = MaterialTheme.colorScheme.secondary
//                    ),
//                ) {
//                    Icon(
//                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
//                        contentDescription = null,
//                        tint = MaterialTheme.colorScheme.background,
//                        modifier = Modifier
//                            .size(60.dp)
//                    )
//                }
//            }
//        }
//    }
//}