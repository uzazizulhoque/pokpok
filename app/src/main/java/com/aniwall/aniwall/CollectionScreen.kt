// File path: app/src/main/java/com/aniwall/aniwall/CollectionScreen.kt
package com.aniwall.aniwall

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun CollectionScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Your Unlocked Wallpapers Collection", style = MaterialTheme.typography.titleMedium)
    }
}
