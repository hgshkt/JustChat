package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.models.Chat

@Composable
fun ChatItem(chat: Chat) {
    Text("name: ${chat.name}")
    Spacer(modifier = Modifier.height(200.dp))
}