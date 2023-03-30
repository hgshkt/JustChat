package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Send
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
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

    val constraints = ConstraintSet {
        val lazyColumn = createRefFor("lazyColumn")
        val textField = createRefFor("textField")
        val image = createRefFor("image")

        constrain(image) {
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom)
            width = Dimension.value(50.dp)
            height = Dimension.value(50.dp)
        }
        constrain(textField) {
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start)
            end.linkTo(image.start)
            top.linkTo(image.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(lazyColumn) {
            top.linkTo(parent.top)
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(textField.top)
            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    }

    val lazyListState = rememberLazyListState()

    if (messages.isNotEmpty()) {
        LaunchedEffect(messages) {
            lazyListState.scrollToItem(messages.lastIndex)
        }
    }

    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.layoutId("lazyColumn"),
            state = lazyListState
        ) {
            items(messages.size) {
                MessageItem(messages[it])
            }
        }
        TextField(
            modifier = Modifier
                .layoutId("textField"),
            placeholder = { Text("Message") },
            value = messageText.value,
            onValueChange = {
                messageText.value = it
            })
        Image(
            painter = rememberVectorPainter(image = Icons.Default.Send),
            contentDescription = "send",
            modifier = Modifier
                .layoutId("image")
                .clickable {
                    viewModel.sendMessage()
                }
        )
    }
}