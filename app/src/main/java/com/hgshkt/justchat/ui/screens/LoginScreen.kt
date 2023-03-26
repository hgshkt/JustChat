package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hgshkt.justchat.ui.theme.ButtonBack
import com.hgshkt.justchat.ui.theme.ChatListBackground

@Preview
@Composable
fun LoginScreen(

) {
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
                text = "Login",
                style = TextStyle(
                    fontSize = 34.sp
                )
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(12.dp),
                placeholder = { Text(text = "Email") },
                value = "",
                onValueChange = {

                }
            )
            TextField(
                modifier = Modifier
                    .fillMaxWidth(0.8f)
                    .padding(12.dp),
                placeholder = { Text(text = "Password") },
                value = "",
                onValueChange = {

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

                }
            ) {
                Text(
                    text = "Login",
                    style = TextStyle(
                        fontSize = 16.sp
                    )
                )
            }
            Button(
                modifier = Modifier
                    .fillMaxWidth(0.4f),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = ButtonBack
                ),
                shape = RoundedCornerShape(20.dp),
                onClick = {

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