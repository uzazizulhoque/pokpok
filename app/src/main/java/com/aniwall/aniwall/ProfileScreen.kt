package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ProfileScreen(name: String, email: String, coins: Long, onSignOut: () -> Unit, onTopUp: () -> Unit) {
    Column(Modifier.fillMaxSize().padding(top = 22.dp), horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(Icons.Default.AccountCircle, null, modifier = Modifier.size(88.dp), tint = MaterialTheme.colorScheme.primary)
        Spacer(Modifier.height(12.dp))
        Text(name, style = MaterialTheme.typography.headlineSmall)
        Text(email, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(22.dp))
        Card(Modifier.fillMaxWidth()) { Column(Modifier.padding(18.dp)) {
            Text("Ani Coins", style = MaterialTheme.typography.titleMedium)
            Text("🪙 $coins", style = MaterialTheme.typography.headlineMedium)
            Spacer(Modifier.height(10.dp)); Button(onClick = onTopUp, modifier = Modifier.fillMaxWidth()) { Text("Buy coins") }
        } }
        Spacer(Modifier.height(18.dp))
        OutlinedButton(onClick = onSignOut, modifier = Modifier.fillMaxWidth()) { Text("Log out") }
    }
}
