package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.homehive.R
import com.example.homehive.viewmodels.isDarkTheme


@Composable
fun PrivacyBox() {


    var isOpen = remember { mutableStateOf(false) }

    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 250.dp else 100.dp,
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
                .fillMaxWidth()
                .clickable{ isOpen.value = !isOpen.value },
            shadowElevation = 5.dp,

            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = if(isDarkTheme.value) R.drawable.dunesdark else R.drawable.dunes),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .offset { IntOffset(x = 0  , y = -100) }
                )
                Text(
                    text = "Privacy",
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
                    Column(){
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp, start = 25.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(R.string.permissions))
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,

                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp, start = 25.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(R.string.privacy))
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,

                                fontWeight = FontWeight.Bold,
                            )
                        }
                        Box(modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 25.dp, bottom = 25.dp, start = 25.dp)
                        ) {
                            Text(
                                text = buildAnnotatedString {
                                    append(stringResource(R.string.tos))
                                },
                                fontSize = MaterialTheme.typography.bodyMedium.fontSize,
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onPrimary,

                                fontWeight = FontWeight.Bold,
                            )
                        }
                    }

                }
            }
        }
    }
}

