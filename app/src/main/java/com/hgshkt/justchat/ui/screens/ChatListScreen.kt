package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.mapToValueList
import com.hgshkt.justchat.models.Chat

@Composable
fun ChatListScreen() {

    var idList by remember { mutableStateOf(emptyList<String>()) }
    var chatList by remember {
        mutableStateOf(emptyList<Chat>())
    }

    LaunchedEffect(AppAuth().currentUserFID) {
        val user = UserDatabaseImpl().getUserByFID(AppAuth().currentUserFID!!)
        idList = mapToValueList(user!!.chatIdMap)
        chatList = loadChats(idList)
    }

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        items(chatList.size) {
            ChatItem(chatList[it])
        }
    }
}

@Composable
private fun ChatItem(chat: Chat) {
    Text("name: ${chat.name}")
    Spacer(modifier = Modifier.height(200.dp))
}

private suspend fun loadChats(idList: List<String>): List<Chat> {
    val chats = mutableListOf<Chat>()
    idList.forEach {
        val chat = ChatDatabaseImpl().getChatById(it)
        chats.add(chat)
    }
    return chats
}