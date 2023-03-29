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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.models.Message
import com.hgshkt.justchat.ui.items.MessageItem

@Preview
@Composable
fun ChatScreen() {
    // fakeMessages creating for ChatScreen preview
    val fakeMessages = listOf(
        Message("m1", "sqvFngw8B9chPZqvr6h15RMMD5U2"),
        Message("m2", "sqvFngw8B9chPZqvr6h15RMMD5U2"),
        Message("m3", AppAuth().currentUserFID!!),
        Message("m4", "vcV16uxLCuPUOxswSFfvn6ebFu63"),
        Message("m5", "sqvFngw8B9chPZqvr6h15RMMD5U2"),
        Message("m6", "vcV16uxLCuPUOxswSFfvn6ebFu63"),
    )
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
                items(fakeMessages.size) {
                    MessageItem(fakeMessages[it])
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
                    value = "",
                    onValueChange = {

                    })
                Image(
                    alignment = Alignment.Center,
                    painter = rememberVectorPainter(image = Icons.Default.Send),
                    contentDescription = "send",
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(8.dp)
                        .clickable {

                        }
                )
            }
        }
    }
}