// File path: app/src/main/java/com/aniwall/aniwall/TopUpDialog.kt
package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TopUpDialog(onDismiss: () -> Unit, onBuyCoins: (Int, String) -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(text = "Top-up Coins") },
        text = {
            Column {
                Button(onClick = { onBuyCoins(100, "$0.99") }, modifier = Modifier.fillMaxWidth()) {
                    Text("100 Coins - $0.99")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onBuyCoins(200, "$1.99") }, modifier = Modifier.fillMaxWidth()) {
                    Text("200 Coins - $1.99")
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}
