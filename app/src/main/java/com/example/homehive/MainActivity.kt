package com.example.homehive

import android.Manifest
import android.content.Context
import android.content.IntentFilter
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.homehive.MyIntent.Companion.DEVICE_ID
import com.example.homehive.library.FavoritesArray
import com.example.homehive.library.createNotificationChannel
import com.example.homehive.ui.theme.HomeHiveTheme
import com.example.homehive.viewmodels.isDarkTheme
import com.example.homehive.viewmodels.isShowRoutines
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionRequired
import com.google.accompanist.permissions.PermissionState
import com.google.accompanist.permissions.rememberPermissionState
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private lateinit var receiver: SkipNotificationReceiver
    @OptIn(ExperimentalPermissionsApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            HomeHiveTheme() {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    val permissionState =
                        rememberPermissionState(permission = Manifest.permission.POST_NOTIFICATIONS)
                    NotificationPermission(permissionState = permissionState)
                    if (!permissionState.hasPermission) {
                        NotificationPermission(permissionState = permissionState)
                        LaunchedEffect(true) {
                            permissionState.launchPermissionRequest()
                        }
                    }

                }

                val deviceId = intent?.getStringExtra(MyIntent.DEVICE_ID)
                if (deviceId != null) {
                    Text(
                        text = stringResource(R.string.message, deviceId)
                    )
                }

                // Application context can be used to access Shared Preferences throughout the app
                val context = LocalContext.current
                val sharedPrefs: SharedPreferences = context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

                isDarkTheme.value = getPersistedValue(sharedPrefs, "theme")
                isShowRoutines.value = getPersistedValue(sharedPrefs, "preference")

                saveListIfAbsent(sharedPrefs, listOf(""), "FavoritesList")
                FavoritesArray.array = getPersistedList(sharedPrefs, "FavoritesList").toMutableList()

                NavHost(startDestination = "home")
            }
        }
    }

    var resumeBoolean : Boolean = true
    override fun onResume() {
        resumeBoolean = true
        receiver = SkipNotificationReceiver(resumeBoolean)
        IntentFilter(MyIntent.SHOW_NOTIFICATION)
            .apply { priority = 1 }
            .also { registerReceiver(receiver, it) }
        super.onResume()
    }
    override fun onStop() {
        resumeBoolean = false
        super.onStop()

        unregisterReceiver(receiver)
    }

    @OptIn(ExperimentalPermissionsApi::class)
    @Composable
    fun NotificationPermission(
        permissionState: PermissionState,
    ) {
        PermissionRequired(
            permissionState = permissionState,
            permissionNotGrantedContent = { /* TODO: función para infromarle al usuario de la necesidad de otrogar el permiso */ },
            permissionNotAvailableContent = { /* TODO: función hacer las adecuaciones a la App debido a que el permiso no fue otorgado  */ }
        ) {
            /* Hacer uso del recurso porque el permiso fue otorgado */
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
                                title = stringResource(id = R.string.home),
                                contentDescription = "Go to home screen",
                                painter = painterResource(id = R.drawable.home),
                            ),
                            MenuItem(
                                id = "routines",
                                title = stringResource(id = R.string.routines),
                                contentDescription = "Go to routines screen",
                                painter = painterResource(id = R.drawable.routine),
                            ),
                            MenuItem(
                                id = "favorites",
                                title = stringResource(id = R.string.favorites),
                                contentDescription = "Go to favorites screen",
                                painter = painterResource(id = R.drawable.heart_filled),
                            ),
                            MenuItem(
                                id = "history",
                                title = stringResource(id = R.string.history),
                                contentDescription = "Go to history screen",
                                painter = painterResource(id = R.drawable.history),
                            ),
                            MenuItem(
                                id = "settings",
                                title = stringResource(id = R.string.settings),
                                contentDescription = "Go to settings screen",
                                painter = painterResource(id = R.drawable.settings),
                            ),
                            MenuItem(
                                id = "help",
                                title = stringResource(id = R.string.help),
                                contentDescription = "Get help",
                                painter = painterResource(id = R.drawable.info),
                            ),
                        ),
                        onItemClick = {
                            scope.launch {
                                scaffoldState.drawerState.close()
                                navController.navigate(it.id)
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