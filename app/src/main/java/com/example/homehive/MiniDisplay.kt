package com.example.homehive

import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homehive.network.deviceModels.NetworkResult


@Composable
fun MiniDisplay(
    result: NetworkResult
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .clickable { },
        elevation = CardDefaults.cardElevation(5.dp)
    ) {
        Row(
            modifier = Modifier.padding(5.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 10.dp)
                    .weight(1f)) {
                Text(
                    text = result.id?: "",
                    fontSize = 16.sp
                )
                Text(
                    text = result.name?: "",
                    fontSize = 16.sp
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun MiniDisplayPreview() {
    MiniDisplay(
        result = NetworkResult(id = "abcdefg",
            name = "juan domingo peroncho"
        )
    )
}