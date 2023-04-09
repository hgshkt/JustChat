package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.User

@Composable
fun ChatItem(
    chat: Chat,
    event: (chat: Chat) -> Unit
) {
    val lastMessageAuthorState = remember { mutableStateOf(User()) }

    LaunchedEffect(chat.lastMessageAuthorFid) {
        if (chat.messagesHashMap.isNotEmpty()) {
            val fid = chat.lastMessageAuthorFid
            lastMessageAuthorState.value = UserDao().getUserByFID(fid)!!
        }
    }

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
                    event(chat)
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
                        text = buildString {
                            append(lastMessageAuthorState.value.name)
                            append(": ")
                            append(chat.lastMessageText)
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