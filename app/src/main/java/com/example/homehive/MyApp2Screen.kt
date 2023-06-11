package com.example.homehive

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowLeft
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun MyApp2Screen() {
    TestComponent()
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview
@Composable
fun TestComponent (fridgeVM : FridgeVM = viewModel() ){     // FRIDGE TEST
    val fridgeUIState by fridgeVM.uiState.collectAsState()

    var tempFridgeTemp: String by remember { mutableStateOf("") }
    var tempFreezerTemp: String by remember { mutableStateOf("") }
    var tempMode: String by remember { mutableStateOf("") }
    // En fridgeUIState tengo acceso a las variables del state
    // En fridgeVM tengo acceso a los metodos definidos para este ViewModel en especifico
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 10.dp),
            verticalArrangement = Arrangement.SpaceAround
    ) {
        Box(modifier = Modifier
            .background(Color.Gray)
        ){
            Text(text = "Power = " + fridgeUIState.power.toString())
        }
        Box(modifier = Modifier.background(Color.Gray)){
            Text(text = "Fridge Temperature = " + fridgeUIState.fridgeTemperature.toString())
        }
        Box(modifier = Modifier.background(Color.Gray)){
            Text(text = "Freezer Temperature = " + fridgeUIState.freezerTemperature.toString())
        }
        Box(modifier = Modifier.background(Color.Gray)){
            Text(text = "Mode = " + fridgeUIState.mode.toString())
        }
        // ----------------------------------- ON/OFF FRIDGE ---------------------------------------
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(30.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = { fridgeVM.setFridgeTemperature(fridgeUIState.fridgeTemperature - 1) },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.KeyboardArrowLeft,
                    contentDescription = null,
                    tint = Color.Yellow
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between buttons and temperature box

            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .padding(horizontal = 30.dp, vertical = 8.dp)
            ) {
                Text(
                    text = fridgeUIState.fridgeTemperature.toString(),
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }

            Spacer(modifier = Modifier.width(16.dp)) // Add spacing between buttons and temperature box

            Button(
                onClick = { fridgeVM.setFridgeTemperature(fridgeUIState.fridgeTemperature + 1) },
                modifier = Modifier
                    .size(48.dp)
                    .clip(RoundedCornerShape(14.dp))
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Icon",
                    tint = Color.White
                )
            }
        }

        Button(
            modifier = Modifier
                .padding(vertical = 10.dp),

            onClick = {
            fridgeVM.togglePower()
        }) {
            Text("ON/OFF")
        }

        // -----------------------------------  FRIDGE TEMP ---------------------------------------

        TextField(value = tempFridgeTemp,
            label = {
                Text("Fridge temperature")
            },
            onValueChange = {
                tempFridgeTemp = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            modifier = Modifier
                .padding(vertical = 10.dp),
            onClick = {
            fridgeVM.setFridgeTemperature(tempFridgeTemp.toInt())
        }) {
            Text("Change Temperature")
        }

        // -----------------------------------  FREEZER TEMP ---------------------------------------

        TextField(value = tempFreezerTemp,
            label = {
                Text("Freezer temperature")
            },
            onValueChange = {
                tempFreezerTemp = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            modifier = Modifier
                .padding(vertical = 10.dp),
            onClick = {
            fridgeVM.setFreezerTemperature(tempFreezerTemp.toInt())
        }) {
            Text("Change Temperature")
        }

        // -----------------------------------  MODE ---------------------------------------

        TextField(value = tempMode,
            label = {
                Text("Set Mode")
            },
            onValueChange = {
                tempMode = it
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )
        Button(
            modifier = Modifier
                .padding(vertical = 10.dp),
            onClick = {
            fridgeVM.setMode(tempMode)
        }) {
            Text("Change Mode")
        }
    }
}