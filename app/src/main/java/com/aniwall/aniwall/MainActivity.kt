// File path: app/src/main/java/com/aniwall/aniwall/MainActivity.kt
package com.aniwall.aniwall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            var isDarkTheme by remember { mutableStateOf(false) }
            var coinBalance by remember { mutableStateOf(50) }
            var showTopUpDialog by remember { mutableStateOf(false) }

            MaterialTheme(
                colorScheme = if (isDarkTheme) darkColorScheme() else lightColorScheme()
            ) {
                val navController = rememberNavController()
                
                Scaffold(
                    topBar = {
                        TopAppBar(
                            title = { Text("AniWall") },
                            actions = {
                                Surface(
                                    shape = MaterialTheme.shapes.small,
                                    color = MaterialTheme.colorScheme.primaryContainer,
                                    modifier = Modifier
                                        .padding(end = 12.dp)
                                        .clickable { showTopUpDialog = true }
                                ) {
                                    Row(
                                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        Text(text = "🪙 $coinBalance Coins", style = MaterialTheme.typography.bodyMedium)
                                    }
                                }
                            }
                        )
                    },
                    bottomBar = {
                        val items = listOf(
                            NavigationItem("Home", "home", Icons.Default.Home),
                            NavigationItem("Collection", "collection", Icons.Default.Favorite),
                            NavigationItem("Profile", "profile", Icons.Default.Person),
                        )
                        NavigationBar {
                            val navBackStackEntry by navController.currentBackStackEntryAsState()
                            val currentRoute = navBackStackEntry?.destination?.route

                            items.forEach { item ->
                                NavigationBarItem(
                                    icon = { Icon(item.icon, contentDescription = item.title) },
                                    label = { Text(item.title) },
                                    selected = currentRoute == item.route,
                                    onClick = {
                                        if (currentRoute != item.route) {
                                            navController.navigate(item.route) {
                                                popUpTo(navController.graph.findStartDestination().id) {
                                                    saveState = true
                                                }
                                                launchSingleTop = true
                                                restoreState = true
                                            }
                                        }
                                    }
                                )
                            }
                        }
                    }
                ) { innerPadding ->
                    NavHost(
                        navController = navController,
                        startDestination = "home",
                        modifier = Modifier.padding(innerPadding)
                    ) {
                        composable("home") { HomeScreen(onUnlockWallpaper = { cost ->
                            if (coinBalance >= cost) {
                                coinBalance -= cost
                            } else {
                                showTopUpDialog = true
                            }
                        }) }
                        composable("collection") { CollectionScreen() }
                        composable("profile") { 
                            ProfileScreen(
                                isDarkTheme = isDarkTheme, 
                                onThemeToggle = { isDarkTheme = it }
                            ) 
                        }
                    }

                    if (showTopUpDialog) {
                        TopUpDialog(
                            onDismiss = { showTopUpDialog = false },
                            onBuyCoins = { coinsToAdd, _ ->
                                coinBalance += coinsToAdd
                                showTopUpDialog = false
                            }
                        )
                    }
                }
            }
        }
    }
}

data class NavigationItem(val title: String, val route: String, val icon: ImageVector)
