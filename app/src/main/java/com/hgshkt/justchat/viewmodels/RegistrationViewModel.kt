package com.hgshkt.justchat.viewmodels

import android.content.Intent
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.layout.activities.MainActivity

class RegistrationViewModel(
    private val navController: NavController
) : ViewModel() {
    val name = mutableStateOf("")
    val id = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun registration() {
        AppAuth().registration(
            name = name.value,
            id = id.value,
            email = email.value,
            password = password.value
        )
        val intent = Intent(navController.context, MainActivity::class.java)
        navController.context.startActivity(intent)
    }
}