package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.ui.items.ChatItem
import com.hgshkt.justchat.viewmodels.ChatListViewModel

@Composable
fun ChatListScreen() {
    val viewModel = ChatListViewModel()
    val chatList = viewModel.chatList

    LazyColumn(modifier = Modifier
        .fillMaxWidth()
        .padding(16.dp)){
        items(chatList.size) {
            ChatItem(chatList[it])
        }
    }
}