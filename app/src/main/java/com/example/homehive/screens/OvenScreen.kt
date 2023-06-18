package com.example.homehive.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
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
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import com.example.homehive.R

import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.viewmodels.FridgeVM
import com.example.homehive.viewmodels.OvenVM
import java.io.Console

import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.stringResource
import com.example.homehive.library.AnimatedTextOverflow

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
            "apagado" -> mutableStateOf(GrillMode.OFF)
            "economico" -> mutableStateOf(GrillMode.ECONOMIC)
            else -> mutableStateOf(GrillMode.COMPLETE)
        }
    }


    val convectionMode = remember {
        when (uiState.convectionMode) {
            "apagado" -> mutableStateOf(ConvectionMode.OFF)
            "economico" -> mutableStateOf(ConvectionMode.ECONOMIC)
            else -> mutableStateOf(ConvectionMode.CONVENTIONAL)
        }
    }


    val sourceMode = remember {
        when (uiState.heatMode) {
            "convencional" -> mutableStateOf(SourceMode.CONVENTIONAL)
            "arriba" -> mutableStateOf(SourceMode.ABOVE)
            else -> mutableStateOf(SourceMode.BELOW)
        }
    }

    val ovenTemperature = remember { mutableStateOf(uiState.ovenTemperature) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(680.dp)
            .padding(innerPadding ?: PaddingValues())
            .verticalScroll(rememberScrollState())
    ) {

        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .height(680.dp)
                .padding(vertical = 15.dp, horizontal = 15.dp),
            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.secondary,
        ) {
            Image(
                painter = painterResource(id = R.drawable.fuego),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )

            Column(
                verticalArrangement = Arrangement.Top
            ) {
                Box(){
                    Row(
                        modifier = Modifier
                            .padding(16.dp)
                            .fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = stringResource(id = R.string.oven),
                            fontWeight = FontWeight.Bold,
                            style = MaterialTheme.typography.headlineLarge,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier
                                .padding(16.dp)
                        )
                        Button(
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
                                text = if(ovenState.value) stringResource(id = R.string.turn_off) else stringResource(id = R.string.turn_on),
                                color = MaterialTheme.colorScheme.background,
                            )
                        }
                    }
                }

                Box(
                    contentAlignment = Alignment.Center
                ) {

                    Column(
                        modifier = Modifier
                            .padding(start = 10.dp, end = 10.dp)
                            .align(Alignment.Center),
                        verticalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text =  stringResource(id = R.string.temperature) + " ${ovenTemperature.value}ºC",
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
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
                                ovenVM.setOvenTemperature(newTemperature.toInt())
                            },
                            valueRange = 90f..230f,

                            colors = SliderDefaults.colors(
                                thumbColor = Color(0xFF620606),
                                activeTrackColor = Color(0xFFE3592B),
                                inactiveTrackColor = MaterialTheme.colorScheme.tertiary.copy(alpha = 0.7f),
                            ),
                            modifier = Modifier.padding(horizontal = 10.dp)
                        )

                        // GRILL MODE BUTTONS
                        Text(
                            text = stringResource(id = R.string.grill_mode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
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
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (grillMode.value == GrillMode.OFF) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.off),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )

                            }
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE GRILL----------------------------------------------
                                onClick = {
                                    setGrillModeLocal(GrillMode.ECONOMIC, grillMode)
                                    ovenVM.setGrillMode("eco")
                                },
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (grillMode.value == GrillMode.ECONOMIC) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.economic),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                            Button(
                                onClick = {
                                    // ------------------------------------------UPDATE DIRECTO DE GRILL----------------------------------------------
                                    setGrillModeLocal(GrillMode.COMPLETE, grillMode)
                                    ovenVM.setGrillMode("large")
                                },
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 15.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (grillMode.value == GrillMode.COMPLETE) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.complete),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                        }

                        // CONVECTION MODE BUTTONS
                        Text(
                            text = stringResource(id = R.string.convection_mode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                        )
                        Row(Modifier.padding(vertical = 8.dp)) {
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE CONVECTION----------------------------------------------
                                onClick = {
                                    setConvectionModeLocal(ConvectionMode.OFF, convectionMode)
                                    ovenVM.setConvectionMode("off")
                                          },
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (convectionMode.value == ConvectionMode.OFF) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.off),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE CONVECTION----------------------------------------------
                                onClick = {
                                    setConvectionModeLocal(ConvectionMode.ECONOMIC, convectionMode)
                                    ovenVM.setConvectionMode("eco")
                                },
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (convectionMode.value == ConvectionMode.ECONOMIC) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.economic),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE CONVECTION----------------------------------------------
                                onClick = {
                                    setConvectionModeLocal(ConvectionMode.CONVENTIONAL, convectionMode)
                                    ovenVM.setConvectionMode("normal")
                                },
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 15.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (convectionMode.value == ConvectionMode.CONVENTIONAL) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.conventional),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                        }

                        // SOURCE MODE BUTTONS
                        Text(
                            text = stringResource(id = R.string.heat_mode),
                            style = MaterialTheme.typography.bodyMedium,
                            color = Color(0xFF2B4E5C),
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                        )
                        Row(Modifier.padding(vertical = 8.dp)) {
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE HEAT----------------------------------------------
                                onClick = {
                                    setSourceModeLocal(SourceMode.CONVENTIONAL, sourceMode)
                                    ovenVM.setHeatMode("normal")
                                },
                                shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sourceMode.value == SourceMode.CONVENTIONAL) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.conventional),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE HEAT----------------------------------------------
                                onClick = {
                                    setSourceModeLocal(SourceMode.ABOVE, sourceMode)
                                    ovenVM.setHeatMode("top")
                                },
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sourceMode.value == SourceMode.ABOVE) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.above),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
                            }
                            Button(
                                // ------------------------------------------UPDATE DIRECTO DE HEAT----------------------------------------------
                                onClick = {
                                    setSourceModeLocal(SourceMode.BELOW, sourceMode)
                                    ovenVM.setHeatMode("bottom")
                                },
                                shape = RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 15.dp),
                                modifier = Modifier
                                    .height(45.dp)
                                    .weight(1f),
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = if (sourceMode.value == SourceMode.BELOW) MaterialTheme.colorScheme.tertiary else Color(0xCCF3F3F0)
                                )
                            ) {
                                AnimatedTextOverflow(
                                    text = stringResource(id = R.string.below),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = Color(0xFF2B4E5C)
                                )
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
                )
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
