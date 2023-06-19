package com.example.homehive.screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.boxes.AccountBox
import com.example.homehive.boxes.AppearanceBox
import com.example.homehive.boxes.PrivacyBox
import com.example.homehive.boxes.ProfileBox
import com.example.homehive.viewmodels.SettingsVM

// Color(0xFF7E9694) - Grisesito
// Color(0XFFCBAB45) - Amarillo Oscuro
// Color(0xFFEECC66) - Amarillo Clarito

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SettingsScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    viewModel : SettingsVM = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    Box(
        modifier = Modifier
            .padding(innerPadding ?: PaddingValues())
    ) {
        LazyVerticalStaggeredGrid(
            columns = StaggeredGridCells.Adaptive(300.dp),
            contentPadding = PaddingValues(16.dp),
            modifier = Modifier.fillMaxSize(),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                ProfileBox()
            }
            item {
                AppearanceBox()
            }
            item {
                AccountBox()
            }
            item {
                PrivacyBox()
            }
        }
    }

}


