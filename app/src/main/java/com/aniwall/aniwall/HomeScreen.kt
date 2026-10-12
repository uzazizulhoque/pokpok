// File path: app/src/main/java/com/aniwall/aniwall/HomeScreen.kt
package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(onUnlockWallpaper: (Int) -> Unit) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp)) {
        Text(text = "Explore Wallpapers", style = MaterialTheme.typography.titleLarge)
        Spacer(modifier = Modifier.height(12.dp))
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(10) { index ->
                Card(
                    modifier = Modifier.height(200.dp).fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(4.dp)
                ) {
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.SpaceBetween,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Box(modifier = Modifier.weight(1.dp).fillMaxWidth(), contentAlignment = Alignment.Center) {
                            Text(text = "Wallpaper ${index + 1}")
                        }
                        Button(
                            onClick = { onUnlockWallpaper(10) },
                            modifier = Modifier.padding(8.dp).fillMaxWidth()
                        ) {
                            Text(text = "Unlock (10 🪙)")
                        }
                    }
                }
            }
        }
    }
}
