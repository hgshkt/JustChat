package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.models.Message
import com.hgshkt.justchat.ui.theme.IncomingMessageColor
import com.hgshkt.justchat.ui.theme.OutgoingMessageColor

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
            .background(OutgoingMessageColor)
    ) {
        Text(message.text)
    }
}

@Composable
fun IncomingMessage(message: Message) {
    Row(
        horizontalArrangement = Arrangement.Start,
        modifier = Modifier
            .fillMaxWidth()
            .background(IncomingMessageColor)
    ) {
        Text(message.text)
    }
}