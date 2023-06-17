package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.DrawStyle
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homehive.R
import com.example.homehive.sendCustomNotification


@Composable
fun ProfileBox() {


    var isOpen = remember { mutableStateOf(true) }

    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 100.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(height),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth(),
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.dunes),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .offset { IntOffset(x = 0  , y = -140) }
                )
                Text(
                    text = "Profile",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(16.dp)
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
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Icon(
                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }

                if(isOpen.value) {
                    Column() {

                    }
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .height(130.dp)             // Cambiar Size de la imagen
                        .padding(start = 22.dp, end = 27.dp, bottom = 8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxHeight()
                                .border(
                                    width = 3.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = CircleShape
                                )
                                .padding(3.dp)
                                .border(width = 1.dp, color = Color.DarkGray, shape = CircleShape)
                                .padding(1.dp)
                                .border(
                                    width = 4.dp,
                                    color = MaterialTheme.colorScheme.tertiary,
                                    shape = CircleShape
                                )
                                .padding(4.dp)
                                .border(width = 1.dp, color = Color.DarkGray, shape = CircleShape)

                        ) {
                            Image(
                                painter = painterResource(id = R.drawable.beebee),
                                contentDescription = null,
                                contentScale = ContentScale.FillHeight,
                                modifier = Modifier
                                    .clip(CircleShape)
                            )
                        }

                        Box(modifier = Modifier
                            .fillMaxHeight()
                            .fillMaxWidth(1F)
                        ){
                            Column(modifier = Modifier
                                .padding(30.dp)

                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.username))
                                    },
                                    fontSize = 24.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,

                                    fontWeight = FontWeight.Bold
                                )
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.household))
                                    },
                                    fontSize = 16.sp,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold
                                )

                            }
                        }
                    }

//                    Button(
//                        onClick = {},
//                        elevation = ButtonDefaults.buttonElevation(
//                            defaultElevation = 30.dp,
//                            pressedElevation = 0.0.dp,
//                        ),
//                        modifier = Modifier
//                            .padding(top = 60.dp)
//                            .align(Alignment.TopCenter), // Align the button to the end (top end of the Box)
//                        colors = ButtonDefaults.buttonColors(
//                            containerColor = Color(0xFFEFE5C5),
//                            contentColor = Color(0xFF1C6135)
//                        )
//                    ) {
//                        Text(text = "EDIT",
//                            style = MaterialTheme.typography.bodySmall,
//                            color = Color(0xFFAFA586),
//                            )
//                    }

                }
            }
        }
    }
}
