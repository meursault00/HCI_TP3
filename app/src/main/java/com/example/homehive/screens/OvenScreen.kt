package com.example.homehive.screens

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
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
fun OvenScreen(navController: NavController, innerPadding: PaddingValues?, viewModel : OvenVM = viewModel()) {
    val OvenUIState by viewModel.uiState.collectAsState()

    val uiState by viewModel.uiState.collectAsState()

    val ovenState = remember { mutableStateOf(uiState.power) }

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
            .fillMaxSize()
            .padding(innerPadding ?: PaddingValues())

    ) {

        Surface(
            modifier = Modifier
                .fillMaxHeight()
                .width(600.dp)
                .padding(vertical = 15.dp, horizontal = 15.dp),
            shape = RoundedCornerShape(15.dp),
            color = Color(0xFFEFE5C5)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Text(
                    text = "Oven",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color(0xFF114225),
                    modifier = Modifier
                        .padding(16.dp)
                )
                Button(
                    onClick = { setOvenStateLocal(ovenState) },
                    modifier = Modifier
                        .padding(start = 150.dp, top = 16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFF4CF6D)
                    )
                ) {
                    Text(getButtonLabel(ovenState.value))
                }
            }

            Image(
                painter = painterResource(id = R.drawable.fuego),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
            )

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {

                Column(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp)
                        .align(Alignment.Center)
                ) {
                    Text(
                        text = "Temperature: ${ovenTemperature.value}ºC",
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
                        },
                        valueRange = 90f..230f,

                        colors = SliderDefaults.colors(
                            thumbColor = Color(0xFF3D1F0F),
                            activeTrackColor = Color(0xFFE3592B),
                            inactiveTrackColor = Color(0xFFEDF5F2).copy(alpha = 0.3f),
                        ),
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    // GRILL MODE BUTTONS
                    Text(
                        text = "Grill Mode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2B4E5C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                    )
                    Row(Modifier.padding(vertical = 8.dp)) {
                        Button(
                            onClick = { setGrillModeLocal(GrillMode.OFF, grillMode) },
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (grillMode.value == GrillMode.OFF) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "OFF",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                        Button(
                            onClick = { setGrillModeLocal(GrillMode.ECONOMIC, grillMode) },
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (grillMode.value == GrillMode.ECONOMIC) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "ECONOMIC",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                        Button(
                            onClick = { setGrillModeLocal(GrillMode.COMPLETE, grillMode) },
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 15.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (grillMode.value == GrillMode.COMPLETE) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "COMPLETE",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                    }

                    // CONVECTION MODE BUTTONS
                    Text(
                        text = "Convection Mode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2B4E5C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                    )
                    Row(Modifier.padding(vertical = 8.dp)) {
                        Button(
                            onClick = { setConvectionModeLocal(ConvectionMode.OFF, convectionMode) },
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (convectionMode.value == ConvectionMode.OFF) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "OFF",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                        Button(
                            onClick = { setConvectionModeLocal(ConvectionMode.ECONOMIC, convectionMode) },
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (convectionMode.value == ConvectionMode.ECONOMIC) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "ECONOMIC",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                        Button(
                            onClick = { setConvectionModeLocal(ConvectionMode.CONVENTIONAL, convectionMode) },
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 15.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (convectionMode.value == ConvectionMode.CONVENTIONAL) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "CONV",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                    }

                    // SOURCE MODE BUTTONS
                    Text(
                        text = "Source Mode",
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color(0xFF2B4E5C),
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier.padding(bottom = 10.dp, start = 10.dp)
                    )
                    Row(Modifier.padding(vertical = 8.dp)) {
                        Button(
                            onClick = { setSourceModeLocal(SourceMode.CONVENTIONAL, sourceMode) },
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 0.dp, bottomStart = 15.dp, bottomEnd = 0.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (sourceMode.value == SourceMode.CONVENTIONAL) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "CONV",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                        Button(
                            onClick = { setSourceModeLocal(SourceMode.ABOVE, sourceMode) },
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 0.dp, bottomStart = 0.dp, bottomEnd = 0.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (sourceMode.value == SourceMode.ABOVE) Color(
                                    0xFEFE5C5
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "ABOVE",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                        Button(
                            onClick = { setSourceModeLocal(SourceMode.BELOW, sourceMode) },
                            shape = RoundedCornerShape(topStart = 0.dp, topEnd = 15.dp, bottomStart = 0.dp, bottomEnd = 15.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = if (sourceMode.value == SourceMode.BELOW) Color(
                                    0xFFFEB26B
                                ) else Color(0x54F3F3F0)
                            )
                        ) {
                            Text(
                                text = "BELOW",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodySmall,
                                color = Color(0xFF2B4E5C)
                            )
                        }
                    }
                    Row(Modifier.padding(vertical = 16.dp)){

                        Button(
                            onClick = { navController.navigate("home") },
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp, bottomEnd = 15.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEFE5C5)
                            )
                        ) {
                            Text(
                                text = "EXIT",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF2B4E5C)
                            )
                        }

                        Button(
                            onClick = {
                                viewModel.setOvenTemperature(ovenTemperature.value)
                                viewModel.setPower(ovenState.value)

                                when (grillMode.value) {
                                    GrillMode.OFF -> viewModel.setGrillMode("apagado")
                                    GrillMode.ECONOMIC -> viewModel.setGrillMode("economico")
                                    GrillMode.COMPLETE -> viewModel.setGrillMode("completo")
                                }


                                when (convectionMode.value) {
                                    ConvectionMode.OFF -> viewModel.setConvectionMode("apagado")
                                    ConvectionMode.ECONOMIC -> viewModel.setConvectionMode("economico")
                                    ConvectionMode.CONVENTIONAL -> viewModel.setConvectionMode("convencional")
                                }

                                when (sourceMode.value) {
                                    SourceMode.CONVENTIONAL -> viewModel.setHeatMode("convencional")
                                    SourceMode.ABOVE -> viewModel.setHeatMode("arriba")
                                    SourceMode.BELOW -> viewModel.setHeatMode("abajo")
                                }


                                navController.navigate("home")
                                      },
                            shape = RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp, bottomStart = 15.dp, bottomEnd = 15.dp),
                            modifier = Modifier
                                .height(45.dp)
                                .weight(1f)
                                .padding(horizontal = 8.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color(0xFFEFE5C5)
                            )
                        ) {
                            Text(
                                text = "SAVE",
                                fontWeight = FontWeight.Bold,
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color(0xFF2B4E5C)
                            )
                        }

                    }
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
