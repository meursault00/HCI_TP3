package com.example.homehive.screens

import android.util.Log
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.layout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.R
import com.example.homehive.library.WindowInfo
import com.example.homehive.library.AnimatedTextOverflow
import com.example.homehive.library.rememberWindowInfo
import com.example.homehive.viewmodels.SpeakerVM


@Composable
fun SpeakerScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    speakerVM: SpeakerVM = viewModel()
) {

    val speakerState by speakerVM.uiState.collectAsState()
    val windowInfo = rememberWindowInfo()
    var expanded = remember { // Para el playlist dropdown
        mutableStateOf(false)
    }

    var playlistSongs = speakerState.playlist.result

    val playlists = listOf("Classical", "Pop", "Country", "Rock", "Dance", "Latina")
    var selectedPlaylist = remember { mutableStateOf(playlists[0]) }

    LaunchedEffect(Unit) {
        speakerVM.checkPolling()
    }


    Column (
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues()),
        contentAlignment = Alignment.Center,
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
                            color = Color(0x00000000),
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
                                        text = speakerState.name,
                                        color = Color(0xFFFFFFFF),
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                    )

                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    )
                                    {
                                        GenericDropdownMenuOnOutlinedButtonIcon(
                                            items = playlists,
                                            painter = painterResource(id = R.drawable.playlist),
                                            onItemSelected = { genre ->
                                                selectedPlaylist.value = genre
                                                speakerVM.setGenre(genre.lowercase())
                                            },
                                            expanded = expanded,
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
                                            PlaylistSongsDisplay(
                                                playlistName = speakerState.genre,
                                                items = playlistSongs.map { song ->
                                                    Song(
                                                        name = song.title,
                                                        artist = song.artist,
                                                        album = song.album,
                                                        duration = song.duration
                                                    )
                                                },
                                                songPlayingColor = Color(0xFF3AF77D),
                                                currentSong = speakerState.song.title?: " ",
                                            )

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
                                                                .padding(start = 30.dp, end = 70.dp)
                                                                .graphicsLayer {
                                                                    scaleX =
                                                                        2f // Increase the scale of the thumb horizontally
                                                                    scaleY =
                                                                        2f // Increase the scale of the thumb vertically
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
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 15.dp,
                                        bottom = 15.dp
                                    )
                            ){

                                //Nombre de la cancion
                                AnimatedTextOverflow(
                                    text = speakerState.song.title?: " "
                                )

                                // artista
                                AnimatedTextOverflow(
                                    text = speakerState.song.artist?: "unavailable",
                                    color = Color(0xB9FFFFFF),
                                    style = MaterialTheme.typography.bodySmall,
                                )
                                // slider de posicion en la cancion
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = speakerState.song.progress ?: "0:00",
                                            fontSize = 12.sp,
                                            color = Color(0xB9FFFFFF),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                        Slider(
                                            value = getProgress(
                                                progress = speakerState.song.progress ?: "0:00",
                                                duration = speakerState.song.duration ?: "0:00"
                                            ),
                                            valueRange = 0f..100f,
                                            enabled = false,
                                            onValueChange = { /*TODO*/ },
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color(0xFFFFFFFF),
                                                activeTrackColor = Color(0xFFFFFFFF),
                                                inactiveTrackColor = Color(0x97FFFFFF),
                                                disabledThumbColor = Color(0xFFFFFFFF),
                                                disabledActiveTrackColor = Color(0xFFFFFFFF),
                                                disabledInactiveTrackColor = Color(0x97FFFFFF)
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        )
                                        Text(
                                            text = speakerState.song.duration ?: "0:00",
                                            fontSize = 12.sp,
                                            color = Color(0xB9FFFFFF),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }

                                Row(
                                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                                ){
                                    IconButton(
                                        onClick = {if(speakerState.volume > 0) {speakerVM.decrementVolume()} else { Log.d("HOLA", "GOLA")} },
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
                                        onClick = {
                                            if (speakerVM.uiState.value.status == "playing") {
                                                speakerVM.previousSong()
                                            } },
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
                                        onClick = {
                                            when(speakerState.status) {
                                                "stopped" -> speakerVM.play()
                                                "paused" -> speakerVM.resume()
                                                else -> speakerVM.pause()
                                            }

                                      },
                                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                        shape = CircleShape,
                                        containerColor = Color(0xFFFFFFFF),
                                        modifier = Modifier.size(50.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = if (speakerState.status == "playing") R.drawable.pause else R.drawable.play),
                                            contentDescription = null,
                                            tint = Color(0xFF000000),
                                            modifier = Modifier.size(30.dp)
                                        )
                                    }
                                    IconButton(
                                        onClick = { if (speakerVM.uiState.value.status == "playing") {
                                            speakerVM.nextSong()
                                        } },
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


/*MARGE*/   else -> {
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
                                        text = speakerState.name,
                                        color = Color(0xFFFFFFFF),
                                        style = MaterialTheme.typography.headlineLarge,
                                        fontWeight = FontWeight.Bold,
                                    )
                                    Row(
                                        horizontalArrangement = Arrangement.spacedBy(10.dp),
                                    )
                                    {
                                        GenericDropdownMenuOnOutlinedButtonIcon(
                                            items = playlists,
                                            painter = painterResource(id = R.drawable.playlist),
                                            onItemSelected = { genre ->
                                                selectedPlaylist.value = genre
                                                speakerVM.setGenre(genre.lowercase())
                                            },
                                            expanded = expanded,
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
                                            PlaylistSongsDisplay(
                                                playlistName = speakerState.genre,
                                                items = playlistSongs.map { song ->
                                                    Song(
                                                        name = song.title,
                                                        artist = song.artist,
                                                        album = song.album,
                                                        duration = song.duration
                                                    )
                                                },
                                                songPlayingColor = Color(0xFF3AF77D),
                                                currentSong = speakerState.song.title?: " ",
                                            )
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
                                    .padding(
                                        start = 30.dp,
                                        end = 30.dp,
                                        top = 15.dp,
                                        bottom = 15.dp
                                    )
                            ) {
                                // NOMBRE DE LA CANCION
                                AnimatedTextOverflow(
                                    text = speakerState.song.title?: " "
                                )
                                AnimatedTextOverflow(
                                    text = speakerState.song.artist?: "unavailable",
                                    color = Color(0xB9FFFFFF),
                                    style = MaterialTheme.typography.bodySmall,
                                )

                                // slider de posicion en la cancion
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(5.dp),
                                        modifier = Modifier
                                            .fillMaxWidth()
                                    ) {
                                        Text(
                                            text = speakerState.song.progress ?: "0:00",
                                            fontSize = 12.sp,
                                            color = Color(0xB9FFFFFF),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                        Slider(
                                            value = getProgress(
                                                progress = speakerState.song.progress ?: "0:00",
                                                duration = speakerState.song.duration ?: "0:00"
                                            ),
                                            valueRange = 0f..100f,
                                            enabled = false,
                                            onValueChange = { /*TODO*/ },
                                            colors = SliderDefaults.colors(
                                                thumbColor = Color(0xFFFFFFFF),
                                                activeTrackColor = Color(0xFFFFFFFF),
                                                inactiveTrackColor = Color(0x97FFFFFF),
                                                disabledThumbColor = Color(0xFFFFFFFF),
                                                disabledActiveTrackColor = Color(0xFFFFFFFF),
                                                disabledInactiveTrackColor = Color(0x97FFFFFF)
                                            ),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .weight(1f)
                                        )
                                        Text(
                                            text = speakerState.song.duration ?: "0:00",
                                            fontSize = 12.sp,
                                            color = Color(0xB9FFFFFF),
                                            style = MaterialTheme.typography.bodyMedium,
                                        )
                                    }
                                }

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
                                        onClick = {
                                            if (speakerVM.uiState.value.status == "playing") {
                                            speakerVM.previousSong()
                                        } },
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
                                        onClick = { when(speakerState.status) {
                                            "stopped" -> speakerVM.play()
                                            "paused" -> speakerVM.resume()
                                            else -> speakerVM.pause()
                                        } },
                                        elevation = FloatingActionButtonDefaults.elevation(0.dp),
                                        shape = CircleShape,
                                        containerColor = Color(0xFFFFFFFF),
                                        modifier = Modifier.size(50.dp) // Adjust the size as desired
                                    ) {
                                        Icon(
                                            painter = painterResource(id = if (speakerState.status == "playing") R.drawable.pause else R.drawable.play),
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


@Composable
fun PlaylistSongsDisplay(
    items: List<Song>,
    playlistName: String = "PLAYLIST",
    songPlayingColor : Color = MaterialTheme.colorScheme.surface,
    currentSong: String = "",
) {
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
                text = playlistName,
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.surface
            )
        }

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(items) { song ->
                SongRow(
                    song = song,
                    songColor = if( currentSong == song.name) songPlayingColor else MaterialTheme.colorScheme.surface,
                )
                Divider(color = MaterialTheme.colorScheme.surface, modifier = Modifier.alpha(0.6f))
            }
        }
    }

}
@Composable
fun SongRow(
    song: Song,
    songColor: Color = MaterialTheme.colorScheme.surface
) {
    Row(
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(id = R.drawable.song),
            contentDescription = null,
            modifier = Modifier
                .size(30.dp)
                .padding(start = 10.dp),
            tint = MaterialTheme.colorScheme.surface
        )
        Column(
            modifier = Modifier
                .padding(start = 16.dp)
                .weight(1f)
        ) {
            Text(
                text = song.name?: "Loading...",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = songColor
            )
            Text(
                text = song.artist?: "Loading...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.alpha(0.6f)

            )
            Text(
                text = song.duration?: "Loading...",
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.surface,
                modifier = Modifier.alpha(0.6f)

            )
        }
    }
}
data class Song(
    val name: String?,
    val artist: String?,
    val album: String?,
    val duration: String?,
)


fun getProgress(progress: String, duration: String): Float {
    val durationParts = duration.split(":")
    val minutes = durationParts[0].toFloatOrNull() ?: 0f
    val seconds = durationParts.getOrNull(1)?.toFloatOrNull() ?: 0f
    val totalSeconds = minutes * 60 + seconds

    val progressParts = progress.split(":")
    val minutesProgress = progressParts[0].toFloatOrNull() ?: 0f
    val secondsProgress = progressParts.getOrNull(1)?.toFloatOrNull() ?: 0f
    val totalSecondsProgress = minutesProgress * 60 + secondsProgress
    val progressPercent = (totalSecondsProgress / totalSeconds) * 100
    if(progressPercent.isNaN()) return 0f
    return progressPercent
}


@Composable
fun GenericDropdownMenuOnOutlinedButtonIcon(
    painter: Painter,
    items: List<String>,
    onItemSelected: (String) -> Unit,
    expanded: MutableState<Boolean>
) {

    OutlinedButton(
        onClick = { expanded.value = true},
        modifier= Modifier.size(40.dp),  //avoid the oval shape
        shape = CircleShape,
        border= BorderStroke(1.dp, Color.White),
        contentPadding = PaddingValues(0.dp),  //avoid the little icon
        colors = ButtonDefaults.outlinedButtonColors(contentColor =  Color.White)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = Color(0xFFFFFFFF),
            modifier = Modifier.size(20.dp)
        )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            offset = DpOffset(0.dp, (7).dp),
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                )
                .width(200.dp)
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text  = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = item,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium,

                                )
                        }
                    },
                    onClick = {
                        expanded.value = false
                        onItemSelected(item)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
