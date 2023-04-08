package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hgshkt.justchat.ui.items.UserItem
import com.hgshkt.justchat.viewmodels.ChatMembersViewModel

@Composable
fun ChatMembersScreen(
    id: String,
    navController: NavController
) {
    val viewModel = remember {
        ChatMembersViewModel(
            id = id,
            navController = navController
        )
    }
    val userList = viewModel.userList

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(userList.size) {
            UserItem(user = userList[it]) { user ->
                viewModel.openProfile(user)
            }
        }
    }
}