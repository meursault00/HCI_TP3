package com.example.homehive

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.homehive.ui.theme.HomeHiveTheme


@Composable
fun HomeScreen(
    onNavigateToOtherScreen: (id:Int) -> Unit
) {
    Surface {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            Button(onClick = { onNavigateToOtherScreen(1234) }) {
                Text(
                    text = stringResource(R.string.goto_other_screen),
                    fontSize = 30.sp
                )
            }
        }
    }
}

@Preview(showSystemUi = true, locale = "es")
@Composable
fun HomeScreenPreview() {
    HomeHiveTheme {
        HomeScreen(onNavigateToOtherScreen = {})
    }
}