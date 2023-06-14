package com.example.homehive.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.homehive.R
import com.example.homehive.viewmodels.DevicesVM
import com.example.homehive.viewmodels.SettingsVM

// Color(0xFF7E9694) - Grisesito
// Color(0XFFCBAB45) - Amarillo Oscuro
// Color(0xFFEECC66) - Amarillo Clarito


// viewModel : OvenVM = viewModel())

@Composable
fun SettingsScreen(
    navController: NavController,
    innerPadding: PaddingValues?,
    viewModel : SettingsVM = viewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    Box(modifier = Modifier
        .fillMaxSize()
        .padding(innerPadding ?: PaddingValues())
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            // SETTING TITLE -------------------------------------------------------------------------

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, top = 12.dp, bottom = 4.dp)
            ){
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.settings))
                    },
                    fontSize = 20.sp,
                    color = Color(0xFFEECC66),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 27.dp, bottom = 16.dp)
                .height(2.dp)
                .background(color = Color(0xFF7E9694), shape = RoundedCornerShape(4.dp))
            )

            // PROFILE -------------------------------------------------------------------------

            Row(modifier = Modifier
                .fillMaxWidth()
                .height(130.dp)             // Cambiar Size de la imagen
                .padding(start = 22.dp, end = 27.dp, bottom = 8.dp)
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxHeight()
                        .border(width = 3.dp, color = Color(0XFFCBAB45), shape = CircleShape)
                        .padding(3.dp)
                        .border(width = 1.dp, color = Color.DarkGray, shape = CircleShape)
                        .padding(1.dp)
                        .border(width = 4.dp, color = Color(0xFFEECC66), shape = CircleShape)
                        .padding(4.dp)
                        .border(width = 1.dp, color = Color.DarkGray, shape = CircleShape)

                ) {
                    Image(
                        painter = painterResource(id = R.drawable.beebee),
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .clip(CircleShape)
                    )
                }

                Box(modifier = Modifier
                    .fillMaxHeight()
                    .fillMaxWidth(1F)
                ){
                    Column(modifier = Modifier
                        .padding(30.dp)

                    ) {
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.username))
                            },
                            fontSize = 24.sp,
                            color = Color(0xFF7E9694),
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = buildAnnotatedString {
                                append(stringResource(R.string.household))
                            },
                            fontSize = 16.sp,
                            color = Color(0xFFEECC66),
                            fontWeight = FontWeight.Bold
                        )

                    }
                }
            }

            // ACCOUNT -------------------------------------------------------------------------

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 4.dp)
            ){
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.account))
                    },
                    fontSize = 18.sp,
                    color = Color(0xFFEECC66),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 27.dp, bottom = 12.dp)
                .height(2.dp)
                .background(color = Color(0xFF7E9694), shape = RoundedCornerShape(4.dp))
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 22.dp, bottom = 6.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.edit_profile))
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.manage_account))
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.payment_method))
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }
            // PRIVACY -------------------------------------------------------------------------

            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 4.dp)
            ){
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.privacy))
                    },
                    fontSize = 18.sp,
                    color = Color(0xFFEECC66),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 27.dp, bottom = 12.dp)
                .height(2.dp)
                .background(color = Color(0xFF7E9694), shape = RoundedCornerShape(4.dp))
            )
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 22.dp, bottom = 6.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.permissions))
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.location_services))
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 26.dp, end = 22.dp, bottom = 8.dp)
            ) {
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.tos))
                    },
                    fontSize = 12.sp,
                    color = Color(0xFF7E9694),
                )
            }

            // APPEARANCE -------------------------------------------------------------------------


            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 22.dp, bottom = 8.dp)
            ){
                Text(
                    text = buildAnnotatedString {
                        append(stringResource(R.string.appearance))
                    },
                    fontSize = 18.sp,
                    color = Color(0xFFEECC66),
                    fontWeight = FontWeight.Bold
                )
            }
            Box(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 22.dp, end = 27.dp, bottom = 12.dp)
                .height(2.dp)
                .background(color = Color(0xFF7E9694), shape = RoundedCornerShape(4.dp))
            )







            Row(
                modifier = Modifier
                    .padding(start = 22.dp, end = 27.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 15.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.language))
                        },
                        fontSize = 12.sp,
                        color = Color(0xFF7E9694),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = if ( uiState.language)  Color(0xFFEFE5C5) else Color(0xB4EFE5C5),

                            shape = RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 0.dp,
                                bottomStart = 15.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .clickable {
                            viewModel.toggleLanguage()
                        }
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.en))
                        },
                        fontSize = 12.sp,
                        color = if ( !uiState.language) Color(0xFF7E9694)  else Color.DarkGray ,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )

                }
                Box(
                    modifier = Modifier
                        .background(
                            color = if ( !uiState.language)  Color(0xFFEFE5C5) else Color(0xB4EFE5C5),

                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 15.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 15.dp
                            )
                        )
                        .clickable {
                            viewModel.toggleLanguage()
                        }
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.sp))
                        },
                        fontSize = 12.sp,
                        color = if ( uiState.language) Color(0xFF7E9694)  else Color.DarkGray ,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold

                    )
                }
            }





            Row(
                modifier = Modifier
                    .padding(start = 22.dp, end = 27.dp, bottom = 8.dp, top = 8.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .padding(end = 45.dp)
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.theme))
                        },
                        fontSize = 12.sp,
                        color = Color(0xFF7E9694),
                        modifier = Modifier.padding(8.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .background(
                            color = if (uiState.theme) Color(0xFFEFE5C5) else Color(0xB4EFE5C5),

                            shape = RoundedCornerShape(
                                topStart = 15.dp,
                                topEnd = 0.dp,
                                bottomStart = 15.dp,
                                bottomEnd = 0.dp
                            )
                        )
                        .clickable {
                            viewModel.toggleTheme()
                        }
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.light_mode))
                        },
                        fontSize = 12.sp,
                        color = if (!uiState.theme) Color(0xFF7E9694) else Color.DarkGray,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold
                    )

                }
                Box(
                    modifier = Modifier
                        .background(
                            color = if (!uiState.theme) Color(0xFFEFE5C5) else Color(0xB4EFE5C5),

                            shape = RoundedCornerShape(
                                topStart = 0.dp,
                                topEnd = 15.dp,
                                bottomStart = 0.dp,
                                bottomEnd = 15.dp
                            )
                        )
                        .clickable {
                            viewModel.toggleTheme()
                        }
                ) {
                    Text(
                        text = buildAnnotatedString {
                            append(stringResource(R.string.dark_mode))
                        },
                        fontSize = 12.sp,
                        color = if (uiState.theme) Color(0xFF7E9694) else Color.DarkGray,
                        modifier = Modifier.padding(8.dp),
                        fontWeight = FontWeight.Bold

                    )
                }
            }
        }
    }
}


