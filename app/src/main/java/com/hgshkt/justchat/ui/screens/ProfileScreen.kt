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
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(fid: String?) {
    val user = remember { mutableStateOf(User()) }
    val viewModel = ProfileViewModel(fid)

    LaunchedEffect(fid) {
        user.value = viewModel.getUser()
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