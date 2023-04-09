package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.models.Message
import com.hgshkt.justchat.ui.theme.IncomingMessageColor
import com.hgshkt.justchat.ui.theme.OutgoingMessageColor
import com.hgshkt.justchat.viewmodels.MessageViewModel

@Composable
fun MessageItem(message: Message) {
    if (message.authorFid == AppAuth().currentUserFID) {
        OutgoingMessage(message)
    } else {
        IncomingMessage(message)
    }
}

@Composable
fun OutgoingMessage(message: Message) {
    Row(
        horizontalArrangement = Arrangement.End,
        modifier = Modifier
            .fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .padding(
                    start = 64.dp, top = 8.dp, bottom = 8.dp, end = 24.dp
                )
                .background(OutgoingMessageColor, shape = RoundedCornerShape(8.dp))
        ) {
            Text(
                text = message.text,
                modifier = Modifier.padding(8.dp),
                color = Color.Black,
                fontSize = 16.sp,
            )
        }
    }
}

@Composable
fun IncomingMessage(message: Message) {
    val viewModel = remember { MessageViewModel(message) }
    val user = viewModel.user.value

    Row(
        modifier = Modifier
            .padding(start = 8.dp, top = 8.dp, bottom = 8.dp, end = 64.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            contentScale = ContentScale.Crop,
            painter = rememberImagePainter(user.avatarUri),
            contentDescription = "avatar",
            modifier = Modifier.size(48.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Box(
            modifier = Modifier
                .background(IncomingMessageColor, shape = RoundedCornerShape(8.dp))
                .wrapContentWidth()
        ) {
            Column(
                modifier = Modifier
                    .wrapContentWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.Top
            ) {
                Text(
                    text = user.name,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = message.text,
                    fontSize = 14.sp,
                    maxLines = Int.MAX_VALUE,
                    overflow = TextOverflow.Clip
                )
            }
        }
    }
}