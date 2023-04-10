package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.hgshkt.justchat.viewmodels.ChatItemViewModel

@Composable
fun ChatItem(
    chatId: String,
    event: () -> Unit
) {
    val viewModel = remember { ChatItemViewModel(chatId) }
    val author = viewModel.author.value
    val chat = viewModel.chat.value

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
    ) {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = 8.dp,
            modifier = Modifier
                .fillMaxSize()
                .clickable {
                    event()
                }
        ) {
            Row(
                modifier = Modifier
                    .padding(12.dp)
            ) {
                Image(
                    contentScale = ContentScale.Crop,
                    painter = rememberImagePainter(chat.avatarUri),
                    contentDescription = null,
                    modifier = Modifier
                        .height(50.dp)
                        .width(50.dp)
                )
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .padding(start = 12.dp)
                ) {
                    Text(
                        text = chat.name,
                        style = TextStyle(
                            color = Color.Black,
                            fontSize = 20.sp
                        )
                    )
                    Text(
                        text = if (author.fid.isNotEmpty()) {
                            buildString {
                                append(author.name)
                                append(": ")
                                append(chat.lastMessageText)
                            }
                        } else {
                            "chat is empty"
                        },
                        style = TextStyle(
                            color = Color.Gray,
                            fontSize = 18.sp
                        )
                    )
                }
            }
        }
    }
}