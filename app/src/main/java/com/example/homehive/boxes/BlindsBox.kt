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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.homehive.R
import com.example.homehive.screens.CurtainState

@Composable
fun BlindsBox(onClick: () -> Unit) {
    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 300.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )

    val curtainState = remember { mutableStateOf(CurtainState.OPEN) }
    val curtainPosition = remember { mutableStateOf(3) }

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
            color = Color(0xFFF4CF6D)
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
                        .offset { IntOffset(x = curtainPosition.value , y = 0) }
                )
                Text(
                    text = "Blinds",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF114225),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )

                Text(
                    text = "${curtainPosition.value}%",
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFF114225),
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

                if (curtainPosition.value <= 0 ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
                }
                if(isOpen.value) {
                    Button( // CHECKEAR CONDICIONES DE ESTADO
                        onClick = { toggleCurtainState(curtainState) },
                        elevation = ButtonDefaults.buttonElevation(
                            defaultElevation = 30.dp,
                            pressedElevation = 0.0.dp,
                        ),
                        modifier = Modifier
                            .padding(bottom = 100.dp)
                            .align(Alignment.Center), // Align the button to the end (top end of the Box)
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFEFE5C5),

                        )
                    ) {
                        Text(
                            getButtonLabel(curtainState.value),
                            color = Color(0xFFAFA586)

                            )
                    }
                    Slider(
                        value = curtainPosition.value.toFloat(),
                        onValueChange = { position -> setPosition(position.toInt(), curtainPosition, curtainState) },
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


private fun setPosition(position: Int, curtainPosition: MutableState<Int>, curtainState: MutableState<CurtainState>) {
    curtainPosition.value = position
    curtainState.value = when (position) {
        0 -> CurtainState.CLOSED
        100 -> CurtainState.OPEN
        else -> {
            when (curtainState.value) {
                CurtainState.CLOSED, CurtainState.CLOSING -> CurtainState.OPENING
                CurtainState.OPEN, CurtainState.OPENING -> CurtainState.CLOSING
            }
        }
    }
}


private fun toggleCurtainState(curtainState: MutableState<CurtainState>) {
    curtainState.value = when (curtainState.value) {
        CurtainState.OPEN -> CurtainState.OPENING
        CurtainState.OPENING -> CurtainState.CLOSED
        CurtainState.CLOSED -> CurtainState.CLOSING
        CurtainState.CLOSING -> CurtainState.OPEN
    }
}

private fun getButtonLabel(curtainState: CurtainState): String {
    return when (curtainState) {
        CurtainState.OPEN -> "Open"
        CurtainState.CLOSING -> "Closing..."
        CurtainState.CLOSED -> "Close"
        CurtainState.OPENING -> "Opening..."
    }
}