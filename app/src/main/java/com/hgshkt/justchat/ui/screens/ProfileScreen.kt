package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(fid: String?) {
    val viewModel = remember {
        ProfileViewModel(fid)
    }
    val user = viewModel.user.value

    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "name: ${user.name}")
        Text(text = "id: ${user.id}")
        Text(text = "bio: ${user.bio}")
    }
}