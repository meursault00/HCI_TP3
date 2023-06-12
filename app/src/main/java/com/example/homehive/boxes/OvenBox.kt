package com.example.homehive.boxes

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.homehive.R


@Composable
fun OvenBox(onClick: () -> Unit) {

    var isOpen = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .height(200.dp)
                .width(200.dp)
                .padding(vertical = 15.dp, horizontal = 15.dp)
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFEFE5C5)
        ) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.fuego),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                )
                Text(
                    text = "Oven",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF114225),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )
                Text(
                    text = "On\n" +
                            "Temp: 120ºC",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.DarkGray,
                    modifier = Modifier.padding(16.dp).align(Alignment.CenterStart)
                )
                Button(
                    onClick = { isOpen.value = !isOpen.value },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.BottomEnd),


                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (isOpen.value) Color(0xFFEFE5C5) else Color(0xFFEEE5C9)),
                ) {
                    Text(
                        text = if (isOpen.value) "OFF" else "ON",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.DarkGray,
                        textAlign = TextAlign.Center
                    )
                }
                if (!isOpen.value) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Black.copy(alpha = 0.3f))
                    )
                }
                else{
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.Red.copy(alpha = 0.1f))
                    )
                }
            }
        }
    }
}