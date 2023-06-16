package com.example.homehive

import android.media.Image
import android.os.Bundle
import android.widget.Space
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material.*
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Card
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.homehive.ui.theme.HomeHiveTheme
import com.example.homehive.ui.theme.gris
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        createNotificationChannel(this)
        super.onCreate(savedInstanceState)
        setContent {
            HomeHiveTheme() {
                NavHost(startDestination = "home")
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun App(
    navController: NavController,
    content: @Composable (navController : NavController,  innerPadding : PaddingValues?) -> Unit,
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = scaffoldState,
        backgroundColor = MaterialTheme.colorScheme.primary,
        topBar = {
            /*
            TabBar(
                navController,
                modifier = Modifier
                    .padding(top = 90.dp)
                    .height(50.dp)
                    .fillMaxSize()
            )*/
            AppBar(
                navController,
                onNavigationIconClick = {
                    scope.launch {
                        scaffoldState.drawerState.open()
                    }
                }
            )

        },
        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
        drawerContent = {
            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.nav_drawer_bg),
                    contentDescription = null,
                    contentScale = ContentScale.FillBounds,
                    modifier = Modifier.fillMaxSize()
                )
                Column(
                    Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    DrawerHeader()
                    DrawerBody(
                        items = listOf(
                            MenuItem(
                                id = "home",
                                title = "Home",
                                contentDescription = "Go to home screen",
                                painter = painterResource(id = R.drawable.home),
                            ),
                            MenuItem(
                                id = "routines",
                                title = "Routines",
                                contentDescription = "Go to routines screen",
                                painter = painterResource(id = R.drawable.routine),
                            ),
                            MenuItem(
                                id = "settings",
                                title = "Settings",
                                contentDescription = "Go to settings screen",
                                painter = painterResource(id = R.drawable.settings),
                            ),
                            MenuItem(
                                id = "help",
                                title = "Help",
                                contentDescription = "Get help",
                                painter = painterResource(id = R.drawable.info),
                            ),
                        ),
                        onItemClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate("${it.id}")
                            }
                            println("Clicked on ${it.title}")
                        }
                    )
                }
            }
        },
        content = { innerPadding ->
            content(navController = navController, innerPadding = innerPadding)
        }
    )

}