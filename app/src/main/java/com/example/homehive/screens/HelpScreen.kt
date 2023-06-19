package com.example.homehive.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.homehive.R

@Composable
fun HelpScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
) {
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding ?: PaddingValues())
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // SETTING TITLE -------------------------------------------------------------------------


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 22.dp, top = 12.dp)
            ) {
                Text(
                    text =stringResource(R.string.help),

                    fontSize = 20.sp,
                    color = Color(0xFFEECC66),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 27.dp, bottom = 16.dp)
                    .height(2.dp)
                    .background(color = Color(0xFF7E9694), shape = RoundedCornerShape(4.dp))
            )


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 22.dp, end = 22.dp,  bottom = 10.dp)
            ) {
                Text(
                    text = stringResource(R.string.text_help),

                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.mail_1),

                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.mail_2),

                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.mail_3),

                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = stringResource(R.string.mail_4),

                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
