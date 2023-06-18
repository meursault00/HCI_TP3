package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.viewmodels.BlindsVM
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.isDarkTheme


@Composable
fun BlindsBox(onClick: () -> Unit, blindsVM : BlindsVM = viewModel()) {

    val blindState by blindsVM.uiState.collectAsState();

    var isBlindOpen = remember { mutableStateOf(blindState.status == "opened")}
    var isBlindClosed = remember { mutableStateOf(blindState.status == "closed") }
    var isClosing = remember { mutableStateOf(blindState.status == "closing" || blindState.status == "closed")}
    var auxBlindsPosition = remember { mutableStateOf(blindState.position) }


    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )



    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .height(blindsHeight),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .width(200.dp)
                .clickable{ isOpen.value = !isOpen.value },

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
                        .offset { IntOffset(x = auxBlindsPosition.value  , y = 0) }
                )
                Text(
                    text = stringResource(id = R.string.blinds),
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )

                Text(
                    text = "${auxBlindsPosition.value}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.Center)
                )

                if (!isBlindOpen.value) {
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
                Button( // CHECKEAR CONDICIONES DE ESTADO
                    onClick = { blindsVM.toggleBlinds() },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if(!isClosing.value) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.background,
                    )
                ) {
                    Text(
                        text = if(!isClosing.value) stringResource(id = R.string.close) else stringResource(id = R.string.open),
                        color = if(!isClosing.value) MaterialTheme.colorScheme.background else MaterialTheme.colorScheme.secondary

                    )
                }
                Text(
                    text = "${blindState.position}%",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary,
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(top = 70.dp),
                )

                if(isOpen.value) {

                    Slider(
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
                            .align(Alignment.BottomCenter)
                    )
                }
            }
        }
    }
}
