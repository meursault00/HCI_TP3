package com.example.homehive.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.TestComponent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import com.example.homehive.R
import com.example.homehive.TestComponent

enum class CurtainState {
    CLOSED,
    CLOSING,
    OPENING,
    OPEN,
}

@Composable
fun BlindsScreen(navController: NavController, innerPadding: PaddingValues?) {
    val curtainState = remember { mutableStateOf(CurtainState.CLOSED) }
    val curtainPosition = remember { mutableStateOf(0) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(innerPadding ?: PaddingValues())

    ) {

        Surface(
            modifier = Modifier
                .height(300.dp)
                .width(600.dp)
                .padding(vertical = 15.dp, horizontal = 15.dp),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFF4CF6D)
        ) {
            Text(
                text = "Blinds",
                fontWeight = FontWeight.Bold,
                style = MaterialTheme.typography.headlineLarge,
                color = Color(0xFF114225),
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.TopCenter)
            )
            Image(
                painter = painterResource(id = R.drawable.blinds),
                contentDescription = null,
                contentScale = ContentScale.FillBounds,
                modifier = Modifier
                    .offset { IntOffset(x = curtainPosition.value , y = 0) }
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                contentAlignment = Alignment.TopEnd // Align content to the top end
            ) {
                Column(Modifier.padding(16.dp)) {
                    Button(
                        onClick = { toggleCurtainState(curtainState) },
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.End), // Align the button to the end (top end of the Box)
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF4CF6D)
                        )
                    ) {
                        Text(getButtonLabel(curtainState.value))
                    }

                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(40.dp)
                            .align(Alignment.CenterHorizontally)
                    ) {
                        Text(
                            text = "${curtainPosition.value}",
                            color = Color.Black,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }

                    Slider(
                        value = curtainPosition.value.toFloat(),
                        onValueChange = { position -> setPosition(position.toInt(), curtainPosition, curtainState) },
                        valueRange = 0f..100f,
                        modifier = Modifier
                            .padding(bottom = 8.dp)
                            .align(Alignment.CenterHorizontally),
                    )
                }
            }

        }
    }
}

private fun openCurtain() {
    // Code to open the curtain
}

private fun closeCurtain() {
    // Code to close the curtain
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
        CurtainState.CLOSING -> CurtainState.OPEN
        CurtainState.CLOSED -> CurtainState.OPENING
        CurtainState.OPENING -> CurtainState.CLOSED
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