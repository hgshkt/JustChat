package com.hgshkt.justchat.viewmodels

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.layout.activities.MainActivity
import com.hgshkt.justchat.ui.navigation.Screen

class LoginViewModel(
    private val navController: NavController
) : ViewModel() {

    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun login() {
        AppAuth().login(
            email = email.value,
            password = password.value
        )
        val intent = Intent(navController.context, MainActivity::class.java)
        navController.context.startActivity(intent)
    }

    fun openRegistrationScreen() {
        navController.navigate(Screen.RegistrationScreen.route)
    }
}