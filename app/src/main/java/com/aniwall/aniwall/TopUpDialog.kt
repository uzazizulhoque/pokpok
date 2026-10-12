package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopUpDialog(onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Top up Ani Coins") },
        text = { Column {
            Text("Choose a coin pack. Real purchases must be connected to Google Play Billing before release.")
            Spacer(Modifier.height(12.dp))
            listOf("100 coins", "200 coins", "500 coins", "1,000 coins").forEach { pack ->
                OutlinedButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth().padding(vertical = 3.dp)) { Text(pack) }
            }
        } },
        confirmButton = { TextButton(onClick = onDismiss) { Text("Close") } }
    )
}
