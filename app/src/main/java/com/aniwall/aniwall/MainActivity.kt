package com.aniwall.aniwall

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

private val AniBlack = Color(0xFF101014)
private val AniPurple = Color(0xFF9C6BFF)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(this)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(
                primary = AniPurple, background = AniBlack, surface = Color(0xFF1A1A22),
                onPrimary = Color.White, onBackground = Color.White, onSurface = Color.White
            )) {
                var user by remember { mutableStateOf(FirebaseAuth.getInstance().currentUser) }
                var loading by remember { mutableStateOf(false) }
                var error by remember { mutableStateOf<String?>(null) }
                if (user == null) {
                    LoginScreen(loading = loading, error = error,
                        onLogin = { email, pass ->
                            loading = true; error = null
                            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.trim(), pass)
                                .addOnCompleteListener { task ->
                                    loading = false
                                    if (task.isSuccessful) user = FirebaseAuth.getInstance().currentUser
                                    else error = task.exception?.localizedMessage ?: "Login failed"
                                }
                        },
                        onCreate = { name, email, pass ->
                            loading = true; error = null
                            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email.trim(), pass)
                                .addOnCompleteListener { task ->
                                    if (!task.isSuccessful) {
                                        loading = false; error = task.exception?.localizedMessage ?: "Account creation failed"
                                    } else {
                                        val u = FirebaseAuth.getInstance().currentUser
                                        val data = hashMapOf("name" to name.trim(), "email" to email.trim(), "coins" to 0L, "lastDailyBonus" to "")
                                        if (u != null) com.google.firebase.firestore.FirebaseFirestore.getInstance()
                                            .collection("users").document(u.uid).set(data)
                                            .addOnCompleteListener { loading = false; user = u }
                                        else { loading = false; error = "Account created. Please log in." }
                                    }
                                }
                        })
                } else {
                    HomeScreen(userId = user!!.uid, email = user!!.email ?: "", onSignOut = {
                        FirebaseAuth.getInstance().signOut(); user = null
                    })
                }
            }
        }
    }
}
