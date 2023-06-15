package com.example.homehive.screens

import android.graphics.drawable.Animatable
import android.widget.ProgressBar
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderColors
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.layout.layout
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.WindowInfo
import com.example.homehive.library.AnimatedTextOverflow
import com.example.homehive.rememberWindowInfo
import com.example.homehive.viewmodels.SpeakerVM
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch


@Composable
fun SpeakerScreen(navController: NavController, innerPadding: PaddingValues?, speakerVM: SpeakerVM = viewModel()) {

    val speakerState by speakerVM.uiState.collectAsState();
    val windowInfo = rememberWindowInfo();
    var isPlaying  = speakerState.status == "playing"


    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        when (windowInfo.screenWidthInfo) {
            is WindowInfo.WindowType.Compact -> {
                Surface(
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(15.dp)
                        .height(530.dp)
                        .width(330.dp),
                    shape = RoundedCornerShape(15.dp),
                ) {
                    Column(Modifier.fillMaxSize()) {
                        Surface( // PARTE NEGRA
                            color = Color(0x0),
                            modifier = Modifier
                                .height(300.dp)
                                .width(330.dp),
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 20.dp, end = 20.dp, top = 15.dp, bottom = 15.dp)
                                ) {
                                    Text(
                                        text = "Speaker",
                                        color = Color(0xFFFFFFFF),
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    OutlinedButton(
                                        onClick = { speakerVM.stop()},
                                        modifier= Modifier.size(40.dp),  //avoid the oval shape
                                        shape = CircleShape,
                                        border= BorderStroke(1.dp, Color.White),
                                        contentPadding = PaddingValues(0.dp),  //avoid the little icon
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.White)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.stop),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Row (
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    modifier = Modifier
                                        .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                                ){
                                    Column{ // COLUMNA DE LA PLAYLIST A LA IZQUIERDA
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = Color(0xFF121212),
                                            modifier = Modifier
                                                .height(300.dp)
                                                .width(250.dp),
                                        ) {

                                        }

                                    }
                                    Column{ // COLUMNA DEL VOLUMEN A LA DERECHA
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = Color(0xFF121212),
                                            modifier = Modifier
                                                .height(300.dp)
                                                .width(80.dp),
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.SpaceBetween,
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ){
                                                Row{ // INDICADOR DEL VOLUMEN
                                                    Surface(
                                                        color = Color(0x0),
                                                        modifier = Modifier
                                                            .height(60.dp)
                                                            .width(80.dp),
                                                    ){
                                                        Text (
                                                            modifier = Modifier.padding(top = 20.dp),
                                                            textAlign = TextAlign.Center,
                                                            text = "${speakerState.volume}",
                                                            style = MaterialTheme.typography.headlineLarge,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(0xFFFFFFFF),
                                                        )
                                                    }

                                                }

                                                Row{ // BARRITAS DEL VOLUMEN
                                                    Surface(
                                                        color = Color(0x0),

                                                        modifier = Modifier
                                                            .height(180.dp)
                                                            .width(80.dp),
                                                    ){
                                                        VerticalSlider(
                                                            value = speakerState.volume.toFloat(),
                                                            onValueChange = { newValue -> speakerVM.setVolume(newValue.toInt()) },
                                                            modifier = Modifier
                                                                .alpha(0f)
                                                                .padding(start= 30.dp ,end = 70.dp)
                                                                .graphicsLayer {
                                                                    scaleX = 2f // Increase the scale of the thumb horizontally
                                                                    scaleY = 2f // Increase the scale of the thumb vertically
                                                                },
                                                            steps = 10,
                                                            valueRange = 0f..10f,
                                                        )
                                                        VolumeBar(
                                                            modifier = Modifier
                                                                .padding(bottom = 42.dp)
                                                                .fillMaxWidth(),
                                                            activeBars = speakerState.volume,
                                                            barCount = 10
                                                        )
                                                    }

                                                }

                                            }
                                        }

                                    }
                                }
                            }

                        }

                        Surface( // PARTE VERDE
                            shape = RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 0.dp
                            ),
                            color = Color(0xFF7CE17A),
                            modifier = Modifier
                                .height(230.dp)
                                .width(330.dp),
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
                            ){

                                //Nombre de la cancion
                                AnimatedTextOverflow(text = "Peso Pluma: Bzrp Music Sessions, Vol. 39")

                                // artista
                                Text(
                                    text = "BZRP Music Sessions, Vol. 39",
                                    color = Color(0xB9FFFFFF),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                )
                                // slider de posicion en la cacin
                                Slider(
                                    value = 0.5f,
                                    enabled = false,
                                    onValueChange = { /*TODO*/ },
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFFFFFFFF),
                                        activeTrackColor = Color(0xFFFFFFFF),
                                        inactiveTrackColor = Color(0x97FFFFFF),
                                        disabledThumbColor = Color(0xFFFFFFFF),
                                        disabledActiveTrackColor = Color(0xFFFFFFFF),
                                        disabledInactiveTrackColor = Color(0x97FFFFFF),

                                        ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ){
                                    IconButton(
                                        onClick = {if(speakerState.volume > 0) speakerVM.decrementVolume() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.minus),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = {speakerVM.previousSong()},
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.previous),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    FloatingActionButton(
                                        onClick = { if(isPlaying) speakerVM.pause() else speakerVM.play() },
                                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                        shape = CircleShape,
                                        containerColor = Color(0xFFFFFFFF),
                                        modifier = Modifier.size(50.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = if(isPlaying) R.drawable.pause else R.drawable.play),
                                            contentDescription = null,
                                            tint = Color(0xFF000000),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { speakerVM.nextSong() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.next),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = {if(speakerState.volume < 10) speakerVM.incrementVolume() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.plus),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                            }

                        }
                    }
                }
            }

            is WindowInfo.WindowType.Medium -> {
                Surface(
                    color = Color(0xFF8000FF),
                    modifier = Modifier
                        .padding(15.dp)
                        .height(330.dp)
                        .width(430.dp),
                    shape = RoundedCornerShape(15.dp),
                ){
                    Text(text = "MEDIUM")
                }
            }

            else -> {
                Surface(
                    color = Color(0xFF000000),
                    modifier = Modifier
                        .padding(15.dp)
                        .height(330.dp)
                        .width(640.dp),
                    shape = RoundedCornerShape(15.dp),
                ) {

                    Row(

                    ) {
                        Surface(
                            // PARTE NEGRA
                            color = Color(0x0),
                            modifier = Modifier
                                .height(300.dp)
                                .width(330.dp),
                        ) {
                            Column {
                                Row(
                                    verticalAlignment = Alignment.Top,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(
                                            start = 20.dp,
                                            end = 20.dp,
                                            top = 15.dp,
                                            bottom = 15.dp
                                        )
                                ) {
                                    Text(
                                        text = "Speaker",
                                        color = Color(0xFFFFFFFF),
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    OutlinedButton(
                                        onClick = { speakerVM.stop() },
                                        modifier = Modifier.size(40.dp),  //avoid the oval shape
                                        shape = CircleShape,
                                        border = BorderStroke(1.dp, Color.White),
                                        contentPadding = PaddingValues(0.dp),  //avoid the little icon
                                        colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.stop),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(20.dp)
                                        )
                                    }
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(5.dp),
                                    modifier = Modifier
                                        .padding(start = 5.dp, end = 5.dp, bottom = 5.dp)
                                ) {
                                    Column { // COLUMNA DE LA PLAYLIST A LA IZQUIERDA
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = Color(0xFF121212),
                                            modifier = Modifier
                                                .height(300.dp)
                                                .width(250.dp),
                                        ) {

                                        }

                                    }
                                    Column { // COLUMNA DEL VOLUMEN A LA DERECHA
                                        Surface(
                                            shape = RoundedCornerShape(10.dp),
                                            color = Color(0xFF121212),
                                            modifier = Modifier
                                                .height(200.dp)
                                                .width(80.dp),
                                        ) {
                                            Column(
                                                verticalArrangement = Arrangement.spacedBy(5.dp),
                                                horizontalAlignment = Alignment.CenterHorizontally,
                                            ) {
                                                /*Row { // INDICADOR DEL VOLUMEN
                                                    Surface(
                                                        color = Color(0x0),
                                                        modifier = Modifier
                                                            .height(60.dp)
                                                            .width(80.dp),
                                                    ) {
                                                        Text(
                                                            modifier = Modifier.padding(top = 5.dp),
                                                            textAlign = TextAlign.Center,
                                                            text = "${speakerState.volume}",
                                                            style = MaterialTheme.typography.headlineLarge,
                                                            fontWeight = FontWeight.Bold,
                                                            color = Color(0xFFFFFFFF),
                                                        )
                                                    }

                                                }*/
                                                Row { // BARRITAS DEL VOLUMEN
                                                    Surface(
                                                        color = Color(0x0),

                                                        modifier = Modifier
                                                            .height(200.dp)
                                                            .width(80.dp),
                                                    ) {

                                                        VerticalSlider(
                                                            modifier = Modifier
                                                                .alpha(0f),
                                                            value = speakerState.volume.toFloat(),
                                                            onValueChange = { newValue ->
                                                                speakerVM.setVolume(
                                                                    newValue.toInt()
                                                                )
                                                            },
                                                            steps = 10,
                                                            valueRange = 0f..10f,
                                                        )
                                                        VolumeBar(
                                                            modifier = Modifier
                                                                .padding(bottom = 50.dp)
                                                                .width(80.dp),
                                                            activeBars = speakerState.volume,
                                                            barCount = 10
                                                        )
                                                    }

                                                }

                                            }
                                        }

                                    }
                                }
                            }
                        }
                        Surface(
                            // PARTE VERDE
                            shape = RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 15.dp,
                                bottomStart = 15.dp,
                                bottomEnd = 15.dp
                            ),
                            color = Color(0xFF7CE17A),
                            modifier = Modifier
                                .height(230.dp)
                                .width(330.dp),
                        ) {
                            Column(
                                verticalArrangement = Arrangement.spacedBy(12.dp),
                                horizontalAlignment = Alignment.CenterHorizontally, // Center horizontally
                                modifier = Modifier
                                    .width(100.dp)
                                    .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
                            ) {

                                //Nombre de la cancion
                                AnimatedTextOverflow(text = "Peso Pluma: Bzrp Music Sessions, Vol. 39")

                                // artista
                                Text(
                                    text = "BZRP Music Sessions, Vol. 39",
                                    color = Color(0xB9FFFFFF),
                                    style = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.Bold,
                                )
                                // slider de posicion en la cacin
                                Slider(
                                    value = 0.5f,
                                    enabled = false,
                                    onValueChange = { /*TODO*/ },
                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFFFFFFFF),
                                        activeTrackColor = Color(0xFFFFFFFF),
                                        inactiveTrackColor = Color(0x97FFFFFF),
                                        disabledThumbColor = Color(0xFFFFFFFF),
                                        disabledActiveTrackColor = Color(0xFFFFFFFF),
                                        disabledInactiveTrackColor = Color(0x97FFFFFF),

                                        ),
                                    modifier = Modifier
                                        .fillMaxWidth()
                                )

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ) {
                                    IconButton(
                                        onClick = { if (speakerState.volume > 0) speakerVM.decrementVolume() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.minus),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { speakerVM.previousSong() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.previous),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    FloatingActionButton(
                                        onClick = { if (isPlaying) speakerVM.pause() else speakerVM.play() },
                                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                        shape = CircleShape,
                                        containerColor = Color(0xFFFFFFFF),
                                        modifier = Modifier.size(50.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = if (isPlaying) R.drawable.pause else R.drawable.play),
                                            contentDescription = null,
                                            tint = Color(0xFF000000),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { speakerVM.nextSong() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.next),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { if (speakerState.volume < 10) speakerVM.incrementVolume() },
                                        modifier = Modifier.size(40.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = R.drawable.plus),
                                            contentDescription = null,
                                            tint = Color(0xFFFFFFFF),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                }
                            }

                        }
                    }

                }
            }
        }

    }
}


@Composable
fun VolumeBar(
    modifier: Modifier = Modifier,
    activeBars: Int = 0,
    barCount: Int = 10
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
    ) {
        val barHeight = remember {
            constraints.maxWidth * 1f / (1f * barCount)
        }
        Canvas(modifier = modifier) {
            for(i in 0 until barCount) {
                drawRoundRect(
                    color = if(i in barCount-activeBars until barCount) Color(0xFF7CE17A) else Color.DarkGray,

                    topLeft = Offset(0f, i * barHeight * 2f  ),
                    size = Size(constraints.maxHeight.toFloat(), barHeight ),
                    cornerRadius = CornerRadius(0f)
                )
            }
        }
    }
}



@Composable
fun VerticalSlider(
    value: Float,
    onValueChange: (Float) -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    valueRange: ClosedFloatingPointRange<Float> = 0f..1f,
    /*@IntRange(from = 0)*/
    steps: Int = 0,
    onValueChangeFinished: (() -> Unit)? = null,
    interactionSource: MutableInteractionSource = remember { MutableInteractionSource() },
    colors: SliderColors = SliderDefaults.colors()
){
    Slider(
        colors = colors,
        interactionSource = interactionSource,
        onValueChangeFinished = onValueChangeFinished,
        steps = steps,
        valueRange = valueRange,
        enabled = enabled,
        value = value,
        onValueChange = onValueChange,
        modifier = Modifier
            .graphicsLayer {
                rotationZ = 270f
                transformOrigin = TransformOrigin(0f, 0f)
            }
            .layout { measurable, constraints ->
                val placeable = measurable.measure(
                    Constraints(
                        minWidth = constraints.minHeight,
                        maxWidth = constraints.maxHeight,
                        minHeight = constraints.minWidth,
                        maxHeight = constraints.maxHeight,
                    )
                )
                layout(placeable.height, placeable.width) {
                    placeable.place(-placeable.width, 0)
                }
            }
            .then(modifier)
    )
}