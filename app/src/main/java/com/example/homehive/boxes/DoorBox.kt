package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.viewmodels.BlindsVM


@Composable
fun DoorBox(onClick: () -> Unit, blindsVM : BlindsVM = viewModel()) {

    val blindState by blindsVM.uiState.collectAsState()

    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(blindsHeight)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.tertiary
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.blinds),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .offset { IntOffset(x = blindState.position  , y = 0) }
                )
                Text(
                    text = stringResource(id = R.string.blinds),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )

                Text(
                    text = "${blindState.position}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
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
                    Button( // CHECKEAR CONDICIONES DE ESTADO
                        onClick = {  },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 30.dp,
                            pressedElevation = 0.0.dp,
                        ),
                        modifier = Modifier
                            .padding(bottom = 100.dp)
                            .align(Alignment.Center), // Align the button to the end (top end of the Box)
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.secondary,

                            )
                    ) {
                        Text(
                            text = stringResource(id = R.string.open),
                            color = MaterialTheme.colorScheme.background

                        )
                    }
                    Slider(
                        value = blindState.position.toFloat(),
                        onValueChange = { newPosition -> blindsVM.setPosition(newPosition.toInt()) },
                        valueRange = 0f..100f,
                        modifier = Modifier
                            .padding(bottom = 50.dp, start = 10.dp, end = 10.dp)
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
