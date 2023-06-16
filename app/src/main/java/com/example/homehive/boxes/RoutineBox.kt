package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
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
import androidx.compose.ui.draw.alpha
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
import com.example.homehive.library.AnimatedTextOverflow

@Composable
fun RoutineBox() {
    var isOpen = remember { mutableStateOf(false) }

    val blindsHeight: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 400.dp else 200.dp,
        animationSpec = tween(durationMillis = 100)
    )
    var items = listOf(
        "Action 1 This is a task that probably occupies mutliple char spaces",
        "Action 2 This idds a task that probably occupies mutliple char spaces",
        "Action 3 This is a task that probably occupies mutliple char spaces",
        "Action 4 This is a task that probably occupies mutliple char spaces",
        "Action 5 This is a task that probably occupies mutliple char spaces",
        "Action 6 This is a task that probably occupies mutliple char spaces",
        "Action 7 This is a task that probably occupies mutliple char spaces",
        "Action 8 This is a task that probably occupies mutliple char spaces",
        "Action 9 This is a task that probably occupies mutliple char spaces",
        "Action 10 This is a task that probably occupies mutliple char spaces",
        "Action 11 This is a task that probably occupies mutliple char spaces",
        "Action 12 This is a task that probably occupies mutliple char spaces",
        "Action 13 This is a task that probably occupies mutliple char spaces",
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
                        containerColor = MaterialTheme.colorScheme.onSecondary,

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
                        containerColor = MaterialTheme.colorScheme.onSecondary
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

                    Surface(
                        color = MaterialTheme.colorScheme.onSecondary,
                        shadowElevation = 12.dp,
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .alpha(0.9f)
                            .padding(top = 90.dp, start = 5.dp, end = 5.dp)
                            .height(170.dp)
                            .fillMaxWidth()
                    ){

                        RoutineList(
                            items = items,
                            routineName = "Routine Name"
                        )

                    }

                }

            }
        }
    }
}


@Composable
fun RoutineList(items: List<String>, routineName: String = "Routine Name") {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .padding(5.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ){
            Text(
                text = routineName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.secondary,
                fontSize = 16.sp,
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            itemsIndexed(items) { index, task ->
                val rowBGColor = if (index % 2 == 0) {
                    MaterialTheme.colorScheme.secondary // Even item background color
                } else {
                    MaterialTheme.colorScheme.onSecondary // Odd item background color
                }
                RTaskRow(task = task, bgColor = rowBGColor)
            }
        }
    }
}

@Composable
fun RTaskRow(task: String = "This is a task that probably occupies mutliple char spaces", bgColor: Color = MaterialTheme.colorScheme.secondary) {
    Row(
        modifier = Modifier
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            color = bgColor,
            modifier = Modifier
                .fillMaxWidth()
        ){
            Column(
                modifier = Modifier
                    .weight(1f)
            ) {
                AnimatedTextOverflow(
                    text = task,
                    style = MaterialTheme.typography.bodyMedium,
                    color = if(bgColor == MaterialTheme.colorScheme.onSecondary) MaterialTheme.colorScheme.secondary else MaterialTheme.colorScheme.onSecondary,
                )

            }
        }

    }
}