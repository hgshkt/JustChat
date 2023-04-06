package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.hgshkt.justchat.ui.items.ChatItem
import com.hgshkt.justchat.ui.theme.ChatListBackground
import com.hgshkt.justchat.viewmodels.ChatListViewModel

@Composable
fun ChatListScreen(
    navController: NavController
) {
    val viewModel = remember { ChatListViewModel() }
    val chatList = viewModel.chatList

    LaunchedEffect(chatList) {
        viewModel.observeChatList()
    }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(ChatListBackground)
            .padding(16.dp)
    ) {
        items(chatList.size) {
            ChatItem(chatList[it]) { chat ->
                viewModel.openChat(
                    navController = navController,
                    chat = chat
                )
            }
        }
    }
}