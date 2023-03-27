package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.hgshkt.justchat.ui.theme.ButtonBack
import com.hgshkt.justchat.ui.theme.ChatListBackground
import com.hgshkt.justchat.viewmodels.RegistrationViewModel

@Composable
fun RegistrationScreen(
    navController: NavController
) {
    val viewModel = remember { RegistrationViewModel(navController) }
    val name = viewModel.name.value
    val id = viewModel.id.value
    val email = viewModel.email.value
    val password = viewModel.password.value

    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize()
            .background(ChatListBackground)
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Registration",
                style = TextStyle(
                    fontSize = 34.sp
                )
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(12.dp),
                placeholder = { Text(text = "Name") },
                value = name,
                onValueChange = {
                    viewModel.name.value = it
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(12.dp),
                placeholder = { Text(text = "Id") },
                value = id,
                onValueChange = {
                    viewModel.id.value = it
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(12.dp),
                placeholder = { Text(text = "Email") },
                value = email,
                onValueChange = {
                    viewModel.email.value = it
                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(12.dp),
                placeholder = { Text(text = "Password") },
                value = password,
                onValueChange = {
                    viewModel.password.value = it
                }
            )
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.4f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBack
                ),
                shape = RoundedCornerShape(20.dp),
                onClick = {
                    viewModel.registration()
                }
            ) {
                Text(
                    text = "Registration",
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
        }
    }
}