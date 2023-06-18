package com.example.homehive

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedIconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.contentColor
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.DpOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.PopupProperties
import androidx.navigation.NavController

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppBar(
    navController: NavController,
    onNavigationIconClick: () -> Unit
) {
    var expanded = remember { mutableStateOf(false) }

    val options = listOf(
        DropdownOption(
            stringResource(id = R.string.settings),
            painterResource(id = R.drawable.settings),
            id = R.drawable.settings
        ),
        DropdownOption(
            stringResource(id = R.string.language),
            painterResource(id = R.drawable.language),
            id= R.drawable.language
        ) ,
    )

    Surface(
        modifier = Modifier
            .padding(15.dp)
            .fillMaxWidth(),
        color = MaterialTheme.colorScheme.primary, // FF114225 // 0xFF2C4B42
        shadowElevation = 4.dp,
        shape = RoundedCornerShape(40.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(4.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                IconButton(
                    onClick = onNavigationIconClick,
                    modifier = Modifier.padding(end = 8.dp, top = 4.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Menu,
                        contentDescription = "Toggle Drawer",
                        tint = Color(0xFFEECC66)
                    )
                }
                Box(
                    modifier = Modifier.clickable { navController.navigate("home") }
                ) {
                    Text(
                        text = stringResource(id = R.string.homehive),
                        fontSize = 30.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color(0xFFEECC66)
                    )
                }
            }

            OutlinedIconButton(
                onClick = { expanded.value = true },
                border= BorderStroke(2.dp, MaterialTheme.colorScheme.secondary),
                shape = CircleShape,
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent,
                    contentColor = MaterialTheme.colorScheme.secondary
                )
            ) {
                 Icon(
                    painter = painterResource(id = R.drawable.moreh ),
                    contentDescription = null,
                    modifier = Modifier.size(30.dp)
                )

                DropdownMenu(
                    expanded = expanded.value,
                    onDismissRequest = { expanded.value = false },
                    offset = DpOffset(0.dp, (15).dp),
                    modifier = Modifier
                        .background(
                            color = MaterialTheme.colorScheme.secondary,
                        )
                ) {
                    options.forEach { option ->
                        DropdownMenuItem(
                            onClick = {
                                actionBasedOnIcon(
                                    navController = navController,
                                    id = option.id ,
                                )
                                expanded.value = false
                            },
                            text =  {

                                Row(
                                    verticalAlignment = Alignment.CenterVertically,
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier.fillMaxWidth()
                                ) {
                                    Text(
                                        text = option.title,
                                        color = MaterialTheme.colorScheme.onPrimary
                                    )
                                    Spacer(modifier = Modifier.width(20.dp))
                                    Icon(
                                        painter = option.painter,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                }

                            }
                        )
                    }
                }
            }

        }
    }
}

private fun actionBasedOnIcon(navController: NavController,id: Int){
    when(id){
        R.drawable.settings -> {
            navController.navigate("settings")
        }
        R.drawable.language -> {
            navController.navigate("ssad")

        }
    }
}


@Composable
fun BottomShadow(alpha: Float = 0.1f, height: Dp = 8.dp) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .height(height)
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color.White.copy(alpha = alpha),
                    Color.Transparent,
                )
            )
        )
    )
}
data class DropdownOption(
    val title: String,
    val painter: Painter,
    val id: Int,
)
