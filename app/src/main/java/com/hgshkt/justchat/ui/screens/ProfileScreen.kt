package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.google.firebase.database.FirebaseDatabase
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.tasks.await

@Composable
fun ProfileScreen(userId: String?) {
    val user = remember { mutableStateOf(User()) }

    LaunchedEffect(userId) {
        val profileRef = FirebaseDatabase.getInstance().getReference("users/$userId")
        user.value = profileRef.get().await().getValue(User::class.java)!!
    }

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "name: ${user.value.name}")
        Text(text = "id: ${user.value.id}")
        Text(text = "bio: ${user.value.bio}")
    }
}