package com.hgshkt.justchat.viewmodels

import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.layout.activities.MainActivity
import com.hgshkt.justchat.ui.navigation.Screen

class LoginViewModel(
    private val navController: NavController
) : ViewModel() {

    private val auth = AppAuth()

    val email = mutableStateOf("")
    val password = mutableStateOf("")
    fun login() {
        auth.login(
            email = email.value,
            password = password.value
        ) { isEmailVerified ->
            if (isEmailVerified) {
                val intent = Intent(navController.context, MainActivity::class.java)
                navController.context.startActivity(intent)
            } else {
                Toast.makeText(
                    navController.context,
                    "Email not confirmed.",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    fun openRegistrationScreen() {
        navController.navigate(Screen.RegistrationScreen.route)
    }
}