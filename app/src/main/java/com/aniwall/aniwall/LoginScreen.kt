package com.aniwall.aniwall

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun LoginScreen(loading: Boolean, error: String?, onLogin: (String, String) -> Unit, onCreate: (String, String, String) -> Unit) {
    var createMode by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    Column(Modifier.fillMaxSize().verticalScroll(rememberScrollState()).padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text("AniWall", fontSize = 38.sp, fontWeight = FontWeight.ExtraBold, color = MaterialTheme.colorScheme.primary)
        Text("Your anime wallpaper universe", color = MaterialTheme.colorScheme.onSurfaceVariant)
        Spacer(Modifier.height(28.dp))
        if (createMode) OutlinedTextField(name, { name = it }, label = { Text("Display name") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(email, { email = it }, label = { Text("Email") }, modifier = Modifier.fillMaxWidth(), singleLine = true)
        OutlinedTextField(password, { password = it }, label = { Text("Password (6+ characters)") }, modifier = Modifier.fillMaxWidth(), singleLine = true, visualTransformation = PasswordVisualTransformation())
        if (error != null) { Spacer(Modifier.height(8.dp)); Text(error, color = MaterialTheme.colorScheme.error) }
        Spacer(Modifier.height(16.dp))
        Button(onClick = { if (createMode) onCreate(name, email, password) else onLogin(email, password) }, enabled = !loading && email.isNotBlank() && password.length >= 6 && (!createMode || name.isNotBlank()), modifier = Modifier.fillMaxWidth()) {
            Text(if (loading) "Please wait…" else if (createMode) "Create account" else "Login")
        }
        TextButton(onClick = { createMode = !createMode }) { Text(if (createMode) "Already have an account? Login" else "New here? Create account") }
    }
}
