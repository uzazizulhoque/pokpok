package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

private data class WallItem(val title: String, val subtitle: String, val emoji: String)
private val demoWalls = listOf(
    WallItem("Demon Slayer", "Hashira collection", "🔥"), WallItem("Naruto", "Shinobi legends", "🍥"),
    WallItem("Attack on Titan", "Titan universe", "⚔️"), WallItem("Solo Leveling", "Shadow monarch", "🌑")
)

@Composable
fun HomeScreen(userId: String, email: String, onSignOut: () -> Unit) {
    var selected by remember { mutableStateOf(0) }
    var coins by remember { mutableLongStateOf(0L) }
    var displayName by remember { mutableStateOf(email.substringBefore("@")) }
    var topUp by remember { mutableStateOf(false) }
    var message by remember { mutableStateOf<String?>(null) }
    val db = remember { FirebaseFirestore.getInstance() }
    DisposableEffect(userId) {
        val listener = db.collection("users").document(userId).addSnapshotListener { snap, _ ->
            if (snap != null && snap.exists()) {
                coins = snap.getLong("coins") ?: 0L
                displayName = snap.getString("name")?.takeIf { it.isNotBlank() } ?: email.substringBefore("@")
            } else {
                db.collection("users").document(userId).set(mapOf("name" to displayName, "email" to email, "coins" to 0L, "lastDailyBonus" to ""))
            }
        }
        onDispose { listener.remove() }
    }
    Scaffold(bottomBar = {
        NavigationBar {
            NavigationBarItem(selected == 0, { selected = 0 }, icon = { Icon(Icons.Default.Home, null) }, label = { Text("Home") })
            NavigationBarItem(selected == 1, { selected = 1 }, icon = { Icon(Icons.Default.Collections, null) }, label = { Text("Collection") })
            NavigationBarItem(selected == 2, { selected = 2 }, icon = { Icon(Icons.Default.Person, null) }, label = { Text("Profile") })
        }
    }) { padding ->
        Column(Modifier.fillMaxSize().padding(padding).padding(horizontal = 18.dp)) {
            Row(Modifier.fillMaxWidth().padding(top = 14.dp, bottom = 12.dp), verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) { Text("AniWall", style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold); Text("Hi, $displayName", color = MaterialTheme.colorScheme.onSurfaceVariant) }
                AssistChip(onClick = { topUp = true }, label = { Text("🪙 $coins") }, leadingIcon = { Icon(Icons.Default.Add, null) })
            }
            when (selected) {
                0 -> {
                    Card(Modifier.fillMaxWidth(), colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primaryContainer)) {
                        Column(Modifier.padding(18.dp)) {
                            Text("Daily login bonus", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Text("Claim 20 coins once per day.", color = MaterialTheme.colorScheme.onPrimaryContainer)
                            Spacer(Modifier.height(10.dp))
                            Button(onClick = {
                                val today = SimpleDateFormat("yyyy-MM-dd", Locale.US).format(Date())
                                val ref = db.collection("users").document(userId)
                                db.runTransaction { tx ->
                                    val snap = tx.get(ref)
                                    if (snap.getString("lastDailyBonus") == today) throw IllegalStateException("You already claimed today's bonus.")
                                    tx.update(ref, "coins", (snap.getLong("coins") ?: 0L) + 20L)
                                    tx.update(ref, "lastDailyBonus", today)
                                }.addOnSuccessListener { message = "20 coins added!" }
                                    .addOnFailureListener { message = it.localizedMessage ?: "Could not claim bonus" }
                            }) { Text("Claim 20 coins") }
                        }
                    }
                    Spacer(Modifier.height(20.dp))
                    Text("Explore anime", style = MaterialTheme.typography.titleLarge, fontWeight = FontWeight.Bold)
                    LazyColumn(contentPadding = PaddingValues(vertical = 8.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        items(demoWalls) { wall -> Card(Modifier.fillMaxWidth()) { Row(Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
                            Text(wall.emoji, style = MaterialTheme.typography.headlineLarge); Spacer(Modifier.width(14.dp)); Column { Text(wall.title, fontWeight = FontWeight.Bold); Text(wall.subtitle, color = MaterialTheme.colorScheme.onSurfaceVariant) }
                        } } }
                    }
                }
                1 -> CollectionScreen(coins = coins, onTopUp = { topUp = true })
                else -> ProfileScreen(name = displayName, email = email, coins = coins, onSignOut = onSignOut, onTopUp = { topUp = true })
            }
        }
    }
    if (topUp) TopUpDialog(onDismiss = { topUp = false })
    if (message != null) AlertDialog(onDismissRequest = { message = null }, confirmButton = { TextButton(onClick = { message = null }) { Text("OK") } }, title = { Text("AniWall") }, text = { Text(message ?: "") })
}
