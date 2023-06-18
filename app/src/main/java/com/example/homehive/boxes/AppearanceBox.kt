package com.example.homehive.boxes

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.layout.positionInRoot
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.homehive.R
import com.example.homehive.viewmodels.SettingsVM
import com.example.homehive.viewmodels.isDarkTheme
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppearanceBox(viewModel : SettingsVM = viewModel()) {

    val uiState by viewModel.uiState.collectAsState()

    val languages = listOf("English", "Spanish", "French", "German")
    var selectedLanguage = remember { mutableStateOf(languages[0]) }

    var expanded = remember {
        mutableStateOf(false)
    }
    val icon = if(expanded.value){
        painterResource(id = R.drawable.upicon)
    }else{
        painterResource(id = R.drawable.downicon)

    }

    var isOpen = remember { mutableStateOf(true) }

    val height: Dp by animateDpAsState(
        targetValue = if (isOpen.value) 240.dp else 100.dp,
        animationSpec = tween(durationMillis = 100)
    )

    Box(
        modifier = Modifier
            .fillMaxSize()
            .height(height),
        contentAlignment = Alignment.Center,
    ) {
        Surface(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { isOpen.value = !isOpen.value },

            shape = RoundedCornerShape(15.dp),
            color = MaterialTheme.colorScheme.onSecondaryContainer
        ) {
            Box(
                modifier = Modifier
                    .fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(id = if(isDarkTheme.value) R.drawable.dunesdark else R.drawable.dunes),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier
                        .offset { IntOffset(x = 0  , y = -140) }
                )
                Text(
                    text = "Appearance",
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onPrimary,

                    modifier = Modifier
                        .padding(16.dp)
                        .align(Alignment.TopCenter)
                )

                Button(
                    onClick = { isOpen.value = !isOpen.value },
                    elevation = ButtonDefaults.buttonElevation(
                        defaultElevation = 30.dp,
                        pressedElevation = 0.0.dp,
                    ),
                    shape = RoundedCornerShape(topStart = 15.dp,
                        topEnd = 15.dp,
                        bottomStart = 0.dp,
                        bottomEnd = 0.dp),
                    modifier = Modifier
                        .height(45.dp)
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.secondary
                    ),
                ) {
                    Icon(
                        painter = if (isOpen.value) painterResource(id = R.drawable.upicon) else painterResource(id = R.drawable.downicon),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.background,
                        modifier = Modifier
                            .size(60.dp)
                    )
                }

                if(isOpen.value) {
                    Column(
                        verticalArrangement = Arrangement.SpaceBetween,
                    ){
                        Row(
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                                .fillMaxWidth(),
                        ){
                            Surface(
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier.fillMaxWidth(),
                                shape = RoundedCornerShape(15.dp),
                                shadowElevation = 16.dp
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .fillMaxWidth()
                                ){
                                    Text(text = stringResource(id = R.string.dark_mode),
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onPrimary,
                                        modifier = Modifier
                                            .padding(start = 22.dp)
                                            .align(Alignment.CenterVertically)
                                    )
                                    Switch(
                                        checked = isDarkTheme.value,
                                        onCheckedChange = { isDarkTheme.value = !isDarkTheme.value },
                                        modifier = Modifier
                                            .padding(end = 22.dp)
                                            .align(Alignment.CenterVertically),
                                        colors = SwitchDefaults.colors(
                                            checkedThumbColor = MaterialTheme.colorScheme.secondary,
                                            uncheckedThumbColor = MaterialTheme.colorScheme.background,
                                            checkedTrackColor = Color(0xFF7FAD74),
                                            uncheckedTrackColor = MaterialTheme.colorScheme.secondary
                                        )
                                    )
                                }

                            }
                        }
                        Row(
                            modifier = Modifier
                                .padding(start = 15.dp, end = 15.dp, top = 5.dp, bottom = 5.dp)
                                .fillMaxWidth(),
                        ){
                            Surface(
                                color = MaterialTheme.colorScheme.secondary,
                                modifier = Modifier
                                    .height(50.dp)
                                    .clickable { expanded.value = !expanded.value }
                                    .fillMaxWidth(),
                                shape = RoundedCornerShape(15.dp),
                                shadowElevation = 16.dp
                            ){
                                Row(
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically,
                                    modifier = Modifier
                                        .padding(start = 22.dp, end = 22.dp)
                                        .fillMaxWidth()
                                ) {
                                    LanguageDropdownMenu(
                                        languages = languages,
                                        selectedLanguage = selectedLanguage.value,
                                        onLanguageSelected = { language ->
                                            selectedLanguage.value = language;
                                            viewModel.toggleLanguage()
                                        },

                                        expanded = expanded,

                                    )
                                    Icon(
                                        painter = icon,
                                        contentDescription = null,
                                        tint = MaterialTheme.colorScheme.onPrimary,
                                    )
                                }
                            }
                        }


                        // --------------



                    }

                }
            }
        }
    }
}

@Composable
fun LanguageDropdownMenu(
    languages: List<String>,
    selectedLanguage: String,
    onLanguageSelected: (String) -> Unit,
    expanded: MutableState<Boolean>
) {

    Box {
        Text(
            text = selectedLanguage,
            modifier = Modifier,
            color = MaterialTheme.colorScheme.onPrimary,
            style = MaterialTheme.typography.bodyMedium,

            )

        DropdownMenu(
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false },
            modifier = Modifier
                .background(
                    color = MaterialTheme.colorScheme.secondary,
                )
                .width(200.dp)
        ) {
            languages.forEach { language ->
                DropdownMenuItem(
                    text  = {
                        Row(
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = language,
                                color = MaterialTheme.colorScheme.onPrimary,
                                style = MaterialTheme.typography.bodyMedium,

                                )
                        }
                    },
                    onClick = {
                        expanded.value = false
                        onLanguageSelected(language)
                    },
                    modifier = Modifier.fillMaxWidth()
                )
            }
        }
    }
}
