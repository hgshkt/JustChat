package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.items.UserItem
import com.hgshkt.justchat.viewmodels.FriendListViewModel

@Composable
fun FriendListScreen() {
    val viewModel = FriendListViewModel()

    var idList by remember { mutableStateOf(emptyList<String>()) }
    var userList by remember {
        mutableStateOf(emptyList<User>())
    }

    LaunchedEffect(AppAuth().currentUserFID) {
        idList = viewModel.idList
        userList = viewModel.userList
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(userList.size) {
            UserItem(user = userList[it])
        }
    }
}