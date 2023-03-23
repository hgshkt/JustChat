package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.viewmodels.ChatListViewModel

@Composable
fun ChatListScreen() {
    val viewModel = ChatListViewModel()

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        items(viewModel.chatList.size) {
            ChatItem(viewModel.chatList[it])
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