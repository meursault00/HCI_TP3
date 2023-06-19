package com.example.homehive

import androidx.compose.animation.core.animateDp
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.TabPosition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import androidx.navigation.NavController
import com.example.homehive.screens.SettingsScreen
import com.google.accompanist.pager.ExperimentalPagerApi
import kotlinx.coroutines.launch


@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
fun TabBar(
    navController: NavController,
    innerPadding: PaddingValues = PaddingValues(0.dp),
    modifier: Modifier = Modifier,
) {
    val pagerState = rememberPagerState()
    val pages = listOf(
        "Home",
        "Routines",
        "Settings",
    )

    val indicator = @Composable { tabPositions: List<TabPosition> ->
        CustomIndicator(tabPositions, pagerState)
    }

    val coroutineScope = rememberCoroutineScope();

    ScrollableTabRow(
        modifier = Modifier.padding(top = 90.dp).height(50.dp),
        selectedTabIndex = pagerState.currentPage,
        indicator = indicator,
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.background,
    ) {
        pages.forEachIndexed { index, title ->
            Tab(
                modifier = Modifier.zIndex(6f),
                text = { Text(text = title) },
                selected = pagerState.currentPage == index,
                onClick = {
                      coroutineScope.launch {
                        pagerState.animateScrollToPage(index)
                    }
                },
            )
        }
    }
    HorizontalPager(
        modifier = Modifier.fillMaxSize(),
        pageCount = pages.size,
        state = pagerState,
    ) { page ->
        when (page) {
            0 -> {
                //HomeScreen(navController = navController, innerPadding = innerPadding )
            }
            1 -> {

                // RoutinesScreen(navController = navController, innerPadding = innerPadding)
            }
            2 -> {
                SettingsScreen(navController = navController, innerPadding = innerPadding)
            }

        }
        /*
        Box(Modifier.fillMaxSize()) {
            Text(modifier = Modifier.align(Alignment.Center), text = "Page $page")
        }*/
    }
}

@OptIn(ExperimentalPagerApi::class, ExperimentalFoundationApi::class)
@Composable
private fun CustomIndicator(tabPositions: List<TabPosition>, pagerState: PagerState) {
    val transition = updateTransition(pagerState.currentPage, label = "")
    val indicatorStart by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 50f)
            } else {
                spring(dampingRatio = 1f, stiffness = 1000f)
            }
        }, label = ""
    ) {
        tabPositions[it].left
    }

    val indicatorEnd by transition.animateDp(
        transitionSpec = {
            if (initialState < targetState) {
                spring(dampingRatio = 1f, stiffness = 1000f)
            } else {
                spring(dampingRatio = 1f, stiffness = 50f)
            }
        }, label = ""
    ) {
        tabPositions[it].right
    }

    Box(
        Modifier
            .offset(x = indicatorStart)
            .wrapContentSize(align = Alignment.BottomStart)
            .width(indicatorEnd - indicatorStart)
            .padding(2.dp)
            .fillMaxSize()
            .background(color = MaterialTheme.colorScheme.secondary, RoundedCornerShape(50))
            .border(BorderStroke(2.dp, MaterialTheme.colorScheme.secondary), RoundedCornerShape(50))
            .zIndex(1f)
    )
}

