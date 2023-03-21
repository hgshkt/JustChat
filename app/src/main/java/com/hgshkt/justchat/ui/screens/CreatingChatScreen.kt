package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User

@Composable
fun CreatingChatScreen() {
    var chatNameState by remember { mutableStateOf("") }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Create chat", style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        TextField(modifier = Modifier
            .fillMaxWidth(),
            placeholder = { Text(text = "Chat name") },
            value = chatNameState,
            onValueChange = {
                chatNameState = it
            })
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            FriendList()
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(onClick = { /*TODO createChat()*/ }) {
                Text(text = "Create")
            }
        }
    }
}

@Composable
private fun FriendList() {
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
            UserItem(user = userList[it]) {
                // TODO() add or delete user from inviteList()
            }
        }
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