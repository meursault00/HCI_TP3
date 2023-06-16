package com.example.homehive.boxes

import androidx.appcompat.app.AppCompatDelegate
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateOffsetAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.viewmodels.SettingsVM
import com.example.homehive.viewmodels.isDarkTheme


@Composable
fun AppearanceBox(viewModel : SettingsVM = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    var isOpen = remember { mutableStateOf(false) }

    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 225.dp else 100.dp,
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
            color = Color(0xFFEFE5C5)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "Appearance",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF114225),
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

                if(isOpen.value) {
                    Column(verticalArrangement = Arrangement.SpaceBetween){
                        Row(
                            modifier = Modifier
                                .padding(start = 22.dp, end = 27.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if ( uiState.language)  Color(0xFFF4CF6D) else Color(0xCCF3F3F0),

                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 15.dp,
                                            bottomEnd = 0.dp
                                        )
                                    )
                                    .clickable {
                                        viewModel.toggleLanguage()
                                    }
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.en))
                                    },
                                    fontSize = 16.sp,
                                    color = if ( !uiState.language) Color(0xFF7E9694)  else Color.DarkGray ,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if ( !uiState.language)  Color(0xFFF4CF6D) else Color(0xCCF3F3F0),

                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 15.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 15.dp
                                        )
                                    )
                                    .clickable {
                                        viewModel.toggleLanguage()
                                    }
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.sp))
                                    },
                                    fontSize = 16.sp,
                                    color = if ( uiState.language) Color(0xFF7E9694)  else Color.DarkGray ,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold

                                )
                            }
                        }

                        Row(
                            modifier = Modifier
                                .padding(start = 22.dp, end = 27.dp, bottom = 8.dp, top = 16.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.Center
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (!isDarkTheme.value) Color(0xFFF4CF6D) else Color(0xCCF3F3F0),

                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 15.dp,
                                            bottomEnd = 0.dp
                                        )
                                    )
                                    .clickable {
                                        viewModel.toggleTheme()
                                    }
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.light_mode))
                                    },
                                    fontSize = 16.sp,
                                    color = if (isDarkTheme.value) Color(0xFF7E9694) else Color.DarkGray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold
                                )

                            }
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (isDarkTheme.value) Color(0xFFF4CF6D) else Color(0xCCF3F3F0),

                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 15.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 15.dp
                                        )
                                    )
                                    .clickable {
                                        viewModel.toggleTheme()
                                    }
                            ) {
                                Text(
                                    text = buildAnnotatedString {
                                        append(stringResource(R.string.dark_mode))
                                    },
                                    fontSize = 16.sp,
                                    color = if (!isDarkTheme.value) Color(0xFF7E9694) else Color.DarkGray,
                                    modifier = Modifier.padding(8.dp),
                                    fontWeight = FontWeight.Bold

                                )
                            }
                        }
                    }

                }
            }
        }
    }
}


