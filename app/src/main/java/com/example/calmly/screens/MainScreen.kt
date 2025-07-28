package com.example.calmly.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.calmly.util.Screen
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.navigation.animation.rememberAnimatedNavController


@OptIn(ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class)
@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreen() {
    val navController = rememberAnimatedNavController()
    val screens = listOf(Screen.Meditation, Screen.Sleep)

    // 🔵 Bluish Gradient Brush
    val topBarGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF84C5F0), // Light Sky Blue
            Color(0xFF6EA8E9)  // Cool Blue
        )
    )

    val screenBackgroundGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF6EA8E9), // Cool Blue
            Color(0xFF3D5A99)  // Deep Blue
        )
    )

    val bottomBarGradient = Brush.verticalGradient(
        colors = listOf(
            Color(0xFF4D6EB4),
            Color(0xFF3A4276)
        )
    )

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(brush = screenBackgroundGradient), // Apply to background
        topBar =
        {
            val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
            TopAppBar(
                title = {
                    Text(
                        when (currentRoute) {
                            Screen.Meditation.route -> "Meditation"
                            Screen.Sleep.route -> "Sleep"
                            else -> ""
                        },
                        modifier = Modifier.fillMaxWidth(),
                        textAlign = TextAlign.Center,
                        color = Color.White,
                        fontSize = 24.sp
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent,
                    scrolledContainerColor = Color.Transparent
                ),
                modifier = Modifier.background(brush = topBarGradient)
            )
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color.Transparent,
                modifier = Modifier.background(brush = bottomBarGradient)
            ) {
                val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
                screens.forEach { screen ->
                    NavigationBarItem(
                        selected = screen.route == currentRoute,
                        onClick = {
                            if (currentRoute != screen.route) {
                                navController.navigate(screen.route) {
                                    popUpTo(navController.graph.startDestinationId) {
                                        saveState = true
                                    }
                                    launchSingleTop = true
                                    restoreState = true
                                }
                            }
                        },
                        icon = {
                            Icon(
                                painter = painterResource(screen.icon),
                                contentDescription = screen.label,
                                tint = Color.White
                            )
                        },
                        label = {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = screen.label,
                                    color = if (screen.route == currentRoute) Color.Black else Color.Gray,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontSize = 12.sp
                                )
                                if (screen.route == currentRoute) {
                                    Box(
                                        modifier = Modifier
                                            .height(3.dp)
                                            .width(26.dp)
                                            .background(Color(0xFF1565C0))
                                    )
                                }
                            }
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.White,
                            unselectedIconColor = Color.LightGray,
                            indicatorColor = Color.Transparent
                        )
                    )
                }
            }
        }
    ) { paddingValues ->
        AnimatedNavHost(
            navController = navController,
            startDestination = Screen.Meditation.route,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .background(brush = screenBackgroundGradient),
            enterTransition = {
                slideInHorizontally(initialOffsetX = { 1000 }) + fadeIn()
            },
            exitTransition = {
                slideOutHorizontally(targetOffsetX = { -1000 }) + fadeOut()
            },
            popEnterTransition = {
                slideInHorizontally(initialOffsetX = { -1000 }) + fadeIn()
            },
            popExitTransition = {
                slideOutHorizontally(targetOffsetX = { 1000 }) + fadeOut()
            }
        ) {
            composable(Screen.Meditation.route) {
                MeditationScreen()
            }
            composable(Screen.Sleep.route) {
                SleepScreen()
            }
        }
    }
}