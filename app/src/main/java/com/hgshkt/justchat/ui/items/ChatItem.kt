package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.models.Chat

@Composable
fun ChatItem(
    chat: Chat,
    event: (chat: Chat) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = 4.dp,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    event(chat)
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Text(
                    text = chat.name,
                    style = TextStyle(
                        fontFamily = FontFamily.Monospace
                    )
                )
            }
        }
    }
}