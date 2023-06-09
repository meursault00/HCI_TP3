package com.example.homehive.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.R
import com.example.homehive.library.AnimatedTextOverflow
import com.example.homehive.viewmodels.OvenVM

enum class GrillMode {
    OFF,
    ECONOMIC,
    COMPLETE,
}

enum class ConvectionMode {
    OFF,
    ECONOMIC,
    CONVENTIONAL,
}

enum class SourceMode {
    CONVENTIONAL,
    ABOVE,
    BELOW,
}

@Composable
fun OvenScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    ovenVM : OvenVM = viewModel()
) {
    val OvenUIState by ovenVM.uiState.collectAsState()

    val uiState by ovenVM.uiState.collectAsState()

    val ovenState = remember { mutableStateOf(uiState.power == "on") }

    val grillMode = remember {
        when (uiState.grillMode) {
            "off" -> mutableStateOf(GrillMode.OFF)
            "eco" -> mutableStateOf(GrillMode.ECONOMIC)
            else -> mutableStateOf(GrillMode.COMPLETE)
        }
    }


    val convectionMode = remember {
        when (uiState.convectionMode) {
            "off" -> mutableStateOf(ConvectionMode.OFF)
            "eco" -> mutableStateOf(ConvectionMode.ECONOMIC)
            else -> mutableStateOf(ConvectionMode.CONVENTIONAL)
        }
    }


    val sourceMode = remember {
        when (uiState.heatMode) {
            "normal" -> mutableStateOf(SourceMode.CONVENTIONAL)
            "top" -> mutableStateOf(SourceMode.ABOVE)
            else -> mutableStateOf(SourceMode.BELOW)
        }
    }

    val ovenTemperature = remember { mutableStateOf(uiState.ovenTemperature) }

    Column(
        modifier = Modifier
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Surface(
            modifier = Modifier
                .height(550.dp)
                .height(400.dp)
                .fillMaxSize()
                .padding(vertical = 15.dp, horizontal = 15.dp),
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.secondary,
        ) {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.fuego),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.align(Alignment.BottomCenter)
                )

                Column(
                    verticalArrangement = Arrangement.Top
                ) {
                    Box() {
                        Row(
                            modifier = Modifier
                                .padding(16.dp)
                                .fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(
                                text = OvenUIState.name,
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.headlineLarge,
                                color = MaterialTheme.colorScheme.onPrimary,
                                modifier = Modifier
                                    .padding(16.dp)
                            )
                            Button(
                                border = BorderStroke(1.dp, MaterialTheme.colorScheme.background),
                                onClick = {
                                    ovenVM.togglePower()
                                    setOvenStateLocal(ovenState)
                                },
                                elevation = ButtonDefaults.buttonElevation(
                                    defaultElevation = 12.dp,
                                    pressedElevation = 0.0.dp,
                                ),
                                modifier = Modifier
                                    .padding(top = 16.dp),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = MaterialTheme.colorScheme.secondary
                                )
                            ) {
                                Text(
                                    text = if (ovenState.value) stringResource(id = R.string.turn_off) else stringResource(
                                        id = R.string.turn_on
                                    ),
                                    color = MaterialTheme.colorScheme.background,
                                )
                            }
                        }
                    }

                    Box(
                        contentAlignment = Alignment.Center
                    ) {

                        Surface(
                            color = MaterialTheme.colorScheme.secondary,
                            shadowElevation = 12.dp,
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .alpha(0.95f)
                                .padding(10.dp)
                                .fillMaxSize()
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .padding(15.dp),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    text = stringResource(id = R.string.temperature) + " ${ovenTemperature.value}ºC",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(start = 10.dp)
                                )
                                Slider(
                                    value = ovenTemperature.value.toFloat(),
                                    onValueChange = { newTemperature ->
                                        setTemperatureLocal(
                                            newTemperature.toInt(),
                                            ovenTemperature
                                        )
                                    },
                                    onValueChangeFinished = {
                                        ovenVM.setOvenTemperature(ovenTemperature.value)
                                    },
                                    valueRange = 90f..230f,

                                    colors = SliderDefaults.colors(
                                        thumbColor = Color(0xFF620606),
                                        activeTrackColor = Color(0xFFE3592B),
                                        inactiveTrackColor = Color(0xFFF4CF6D).copy(alpha = 0.7f),
                                    ),
                                    modifier = Modifier.padding(horizontal = 10.dp)
                                )

                                // GRILL MODE BUTTONS
                                Text(
                                    text = stringResource(id = R.string.grill_mode),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                                )
                                Row(Modifier.padding(vertical = 8.dp)) {
                                    Button(
                                        onClick = {
                                            // ------------------------------------------UPDATE DIRECTO DE GRILL----------------------------------------------
                                            setGrillModeLocal(GrillMode.OFF, grillMode)
                                            ovenVM.setGrillMode("off")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 15.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (grillMode.value == GrillMode.OFF) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setGrillModeLocal(GrillMode.OFF, grillMode)
                                                ovenVM.setGrillMode("off")
                                            },
                                            text = stringResource(id = R.string.off),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (grillMode.value == GrillMode.OFF) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )

                                    }
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE GRILL----------------------------------------------
                                        onClick = {
                                            setGrillModeLocal(GrillMode.ECONOMIC, grillMode)
                                            ovenVM.setGrillMode("eco")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (grillMode.value == GrillMode.ECONOMIC) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setGrillModeLocal(GrillMode.ECONOMIC, grillMode)
                                                ovenVM.setGrillMode("eco")
                                            },
                                            text = stringResource(id = R.string.economic),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (grillMode.value == GrillMode.ECONOMIC) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                    Button(
                                        onClick = {
                                            // ------------------------------------------UPDATE DIRECTO DE GRILL----------------------------------------------
                                            setGrillModeLocal(GrillMode.COMPLETE, grillMode)
                                            ovenVM.setGrillMode("large")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 15.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 15.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (grillMode.value == GrillMode.COMPLETE) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setGrillModeLocal(GrillMode.COMPLETE, grillMode)
                                                ovenVM.setGrillMode("large")
                                            },
                                            text = stringResource(id = R.string.complete),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (grillMode.value == GrillMode.COMPLETE) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                }

                                // CONVECTION MODE BUTTONS
                                Text(
                                    text = stringResource(id = R.string.convection_mode),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                                )
                                Row(Modifier.padding(vertical = 8.dp)) {
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE CONVECTION----------------------------------------------
                                        onClick = {
                                            setConvectionModeLocal(
                                                ConvectionMode.OFF,
                                                convectionMode
                                            )
                                            ovenVM.setConvectionMode("off")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 15.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (convectionMode.value == ConvectionMode.OFF) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setConvectionModeLocal(
                                                    ConvectionMode.OFF,
                                                    convectionMode
                                                )
                                                ovenVM.setConvectionMode("off")
                                            },
                                            text = stringResource(id = R.string.off),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (convectionMode.value == ConvectionMode.OFF) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE CONVECTION----------------------------------------------
                                        onClick = {
                                            setConvectionModeLocal(
                                                ConvectionMode.ECONOMIC,
                                                convectionMode
                                            )
                                            ovenVM.setConvectionMode("eco")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (convectionMode.value == ConvectionMode.ECONOMIC) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setConvectionModeLocal(
                                                    ConvectionMode.ECONOMIC,
                                                    convectionMode
                                                )
                                                ovenVM.setConvectionMode("eco")
                                            },
                                            text = stringResource(id = R.string.economic),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (convectionMode.value == ConvectionMode.ECONOMIC) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE CONVECTION----------------------------------------------
                                        onClick = {
                                            setConvectionModeLocal(
                                                ConvectionMode.CONVENTIONAL,
                                                convectionMode
                                            )
                                            ovenVM.setConvectionMode("normal")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 15.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 15.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (convectionMode.value == ConvectionMode.CONVENTIONAL) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setConvectionModeLocal(
                                                    ConvectionMode.CONVENTIONAL,
                                                    convectionMode
                                                )
                                                ovenVM.setConvectionMode("normal")
                                            },
                                            text = stringResource(id = R.string.conventional),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (convectionMode.value == ConvectionMode.CONVENTIONAL) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                }

                                // SOURCE MODE BUTTONS
                                Text(
                                    text = stringResource(id = R.string.heat_mode),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary,
                                    fontWeight = FontWeight.Bold,
                                    modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                                )
                                Row(Modifier.padding(vertical = 8.dp)) {
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE HEAT----------------------------------------------
                                        onClick = {
                                            setSourceModeLocal(
                                                SourceMode.CONVENTIONAL,
                                                sourceMode
                                            )
                                            ovenVM.setHeatMode("normal")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 15.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 15.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (sourceMode.value == SourceMode.CONVENTIONAL) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setSourceModeLocal(
                                                    SourceMode.CONVENTIONAL,
                                                    sourceMode
                                                )
                                                ovenVM.setHeatMode("normal")
                                            },
                                            text = stringResource(id = R.string.conventional),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (sourceMode.value == SourceMode.CONVENTIONAL) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE HEAT----------------------------------------------
                                        onClick = {
                                            setSourceModeLocal(SourceMode.ABOVE, sourceMode)
                                            ovenVM.setHeatMode("top")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 0.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 0.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (sourceMode.value == SourceMode.ABOVE) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setSourceModeLocal(
                                                    SourceMode.ABOVE,
                                                    sourceMode
                                                )
                                                ovenVM.setHeatMode("top")
                                            },
                                            text = stringResource(id = R.string.above),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (sourceMode.value == SourceMode.ABOVE) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                    Button(
                                        // ------------------------------------------UPDATE DIRECTO DE HEAT----------------------------------------------
                                        onClick = {
                                            setSourceModeLocal(SourceMode.BELOW, sourceMode)
                                            ovenVM.setHeatMode("bottom")
                                        },
                                        shape = RoundedCornerShape(
                                            topStart = 0.dp,
                                            topEnd = 15.dp,
                                            bottomStart = 0.dp,
                                            bottomEnd = 15.dp
                                        ),
                                        modifier = Modifier
                                            .height(45.dp)
                                            .weight(1f),
                                        colors = ButtonDefaults.buttonColors(
                                            containerColor = if (sourceMode.value == SourceMode.BELOW) MaterialTheme.colorScheme.inverseOnSurface else Color(
                                                0xCCF3F3F0
                                            )
                                        )
                                    ) {
                                        AnimatedTextOverflow(
                                            modifier = Modifier.clickable {
                                                setSourceModeLocal(
                                                    SourceMode.BELOW,
                                                    sourceMode
                                                )
                                                ovenVM.setHeatMode("bottom")
                                            },
                                            text = stringResource(id = R.string.below),
                                            style = MaterialTheme.typography.bodySmall,
                                            color = if (sourceMode.value == SourceMode.BELOW) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onTertiary
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
                if (!ovenState.value) {
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
                            .background(Color.Transparent)
                    )
                }
            }
        }
    }
}
private fun setTemperatureLocal(temperature: Int, ovenTemperature: MutableState<Int>) {
    ovenTemperature.value = temperature
}

private fun getButtonLabel(ovenState: Boolean): String {
    return if (ovenState) "Turn Off" else "Turn On"
}

private fun setOvenStateLocal(ovenState: MutableState<Boolean>){
    ovenState.value = !ovenState.value
}

private fun setGrillModeLocal(newGrillMode: GrillMode, grillMode: MutableState<GrillMode>){
    grillMode.value = newGrillMode
}

private fun setConvectionModeLocal(newConvectionMode: ConvectionMode, convectionMode: MutableState<ConvectionMode>){
    convectionMode.value = newConvectionMode
}

private fun setSourceModeLocal(newSourceMode: SourceMode, sourceMode: MutableState<SourceMode>){
    sourceMode.value = newSourceMode
}
