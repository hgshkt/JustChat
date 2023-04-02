package com.hgshkt.justchat.viewmodels

import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.ui.navigation.Screen

class RegistrationViewModel(
    private val navController: NavController
) : ViewModel() {
    private val auth = AppAuth()

    val name = mutableStateOf("")
    val id = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun registration() {
        auth.registration(
            email = email.value,
            password = password.value,
            name = name.value,
            customId = id.value
        ) {
            openLoginScreen()
            Toast.makeText(
                navController.context,
                "Link was sent to ${FirebaseAuth.getInstance().currentUser!!.email}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openLoginScreen() {
        navController.navigate(Screen.LoginScreen.route)
    }
}