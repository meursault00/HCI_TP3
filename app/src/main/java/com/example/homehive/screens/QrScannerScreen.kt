package com.example.homehive.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.Camera
import com.example.homehive.R
import com.example.homehive.code
import com.example.homehive.viewmodels.SettingsVM


@Composable
fun QrScannerScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    viewModel : SettingsVM = viewModel()
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shadowElevation = 16.dp,
            color = MaterialTheme.colorScheme.secondary,
            modifier = Modifier
                .padding(15.dp)
                .fillMaxSize(),
            shape = RoundedCornerShape(15.dp),
        ) {

            Column(
                modifier = Modifier
                    .padding(10.dp)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Column(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.add_qr),
                            contentDescription = "qr",
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(50.dp)
                        )
                        Text(
                            text = stringResource(id = R.string.scan_qr),
                            color = MaterialTheme.colorScheme.onPrimary,
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End,
                        )

                    }
                }
                Column(
                    modifier = Modifier
                        .padding(5.dp)
                        .fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Surface(
                        shadowElevation = 16.dp,
                        color = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .fillMaxSize(),
                        shape = RoundedCornerShape(15.dp),
                    ) {
                        Camera()
                        if(code.value.isNotEmpty()){
                            Box(modifier = Modifier.fillMaxSize()) {
                                Surface(
                                    color = MaterialTheme.colorScheme.onSecondary,
                                    modifier = Modifier
                                        .align(Alignment.BottomCenter)
                                        .fillMaxWidth()
                                        .padding(10.dp)
                                        .height(100.dp),
                                    shape = RoundedCornerShape(15.dp),
                                    shadowElevation = 16.dp
                                ) {
                                    Column(
                                        modifier = Modifier.fillMaxSize().padding(5.dp),
                                        verticalArrangement = Arrangement.Center
                                    ) {
                                        Text(
                                            text = code.value,
                                            color = MaterialTheme.colorScheme.secondary,
                                            style = MaterialTheme.typography.headlineSmall,
                                            fontWeight = FontWeight.Bold,
                                            textAlign = TextAlign.End,
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