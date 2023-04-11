package com.hgshkt.justchat.viewmodels

import android.content.Intent
import android.widget.Toast
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.layout.activities.MainActivity
import com.hgshkt.justchat.tools.auth.appAuthRegistration
import com.hgshkt.justchat.tools.auth.entered
import com.hgshkt.justchat.tools.auth.sendEmailVerification

class RegistrationViewModel(
    private val navController: NavController
) : ViewModel() {
    val name = mutableStateOf("")
    val id = mutableStateOf("")
    val email = mutableStateOf("")
    val password = mutableStateOf("")

    fun registration() {
        appAuthRegistration(
            context = navController.context,
            email = email.value,
            password = password.value,
            name = name.value,
            customId = id.value
        ) {
            Toast.makeText(
                navController.context,
                "Link was sent to ${FirebaseAuth.getInstance().currentUser!!.email}",
                Toast.LENGTH_LONG
            ).show()

            openMainScreen()
        }
    }

    fun sendEmail() {
        if (entered) {
            sendEmailVerification(
                context = navController.context,
                email = email.value
            ) {
                Toast.makeText(
                    navController.context,
                    "Link was sent to ${FirebaseAuth.getInstance().currentUser!!.email}",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            Toast.makeText(
                navController.context,
                "Fill in the data",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun openMainScreen() {
        val intent = Intent(navController.context, MainActivity::class.java)
        navController.context.startActivity(intent)
    }
}