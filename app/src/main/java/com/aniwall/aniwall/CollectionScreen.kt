package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Collections
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun CollectionScreen(coins: Long, onTopUp: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(top = 12.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Default.Collections, null, modifier = Modifier.size(56.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(12.dp))
        Text("My Collection", style = MaterialTheme.typography.headlineSmall)
        Spacer(Modifier.height(8.dp))
        Text("Your saved wallpapers will appear here.", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(20.dp))
        Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(18.dp)) {
            Text("Wallet balance", style = MaterialTheme.typography.titleMedium)
            Text("🪙 $coins coins", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(8.dp)); Button(onClick = onTopUp) { Text("Top up coins") }
        } }
    }
}
