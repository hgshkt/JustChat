package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.ui.items.MessageItem
import com.hgshkt.justchat.viewmodels.ChatViewModel

@Composable
fun ChatScreen(
    id: String
) {
    val viewModel = remember {
        ChatViewModel(id)
    }
    val messages = remember {
        viewModel.messages
    }
    val messageText = remember { viewModel.messageText }
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .fillMaxSize()
        ) {
            LazyColumn {
                items(messages.size) {
                    MessageItem(messages[it])
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxHeight(0.11f)
            ) {
                TextField(
                    modifier = Modifier
                        .fillMaxWidth(0.85f),
                    placeholder = { Text("Message") },
                    value = messageText.value,
                    onValueChange = {
                        messageText.value = it
                    })
                Image(
                    alignment = Alignment.Center,
                    painter = rememberVectorPainter(image = Icons.Default.Send),
                    contentDescription = "send",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clickable {
                            viewModel.sendMessage()
                        }
                )
            }
        }
    }
}