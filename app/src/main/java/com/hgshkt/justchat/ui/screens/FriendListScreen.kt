package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User

@Composable
fun FriendListScreen() {

    var idList by remember { mutableStateOf(emptyList<String>()) }
    var userList by remember {
        mutableStateOf(emptyList<User>())
    }

    LaunchedEffect(AppAuth().currentUserFID) {
        idList = UserDatabaseImpl().getFriendList(AppAuth().currentUserFID!!)!!
        userList = loadUsers(idList)
    }

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)) {
        items(userList.size) {
            UserItem(user = userList[it])
        }
    }
}

@Composable
fun UserItem(
    user: User,
    onClickEvent: () -> Unit = {}) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "name: ${user.name}")
        Text(text = "id: ${user.id}")
        Spacer(modifier = Modifier.height(100.dp))
    }
}

private suspend fun loadUsers(idList: List<String>): List<User> {
    val users = mutableListOf<User>()
    idList.forEach {
        val user = UserDatabaseImpl().getUserByFID(it)!!
        users.add(user)
    }
    return users
}