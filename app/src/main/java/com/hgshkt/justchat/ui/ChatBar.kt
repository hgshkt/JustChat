package com.hgshkt.justchat.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Popup
import androidx.compose.ui.window.PopupProperties
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.ui.items.PopupItem
import kotlinx.coroutines.launch

@Composable
fun ChatBar(
    onNavigationIconClick: () -> Unit,
    id: String
) {
    val viewModel = remember { ChatBarViewModel(id) }
    var isMenuExpanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = {
            Text(text = viewModel.chatName.value)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        actions = {
            IconButton(onClick = { isMenuExpanded = true }) {
                Icon(Icons.Filled.MoreVert, contentDescription = "Menu")
            }
        },
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        }
    )

    if (isMenuExpanded) {
        Popup(
            onDismissRequest = { isMenuExpanded = false },
            properties = PopupProperties(focusable = true),
            offset = IntOffset(LocalConfiguration.current.screenWidthDp, -16)
        ) {
            Surface(
                shape = RoundedCornerShape(4.dp),
                elevation = 4.dp,
                color = Color.White,
                modifier = Modifier.size(200.dp)
            ) {
                LazyColumn {
                    items(viewModel.popupItems.size) {
                        Text(
                            text = viewModel.popupItems[it].text,
                            modifier = viewModel.popupItems[it].modifier
                        )
                    }
                }
            }
        }
    }
}

private class ChatBarViewModel(
    id: String
) : ViewModel() {
    val chatName = mutableStateOf("")
    val popupItems = mutableListOf(
        PopupItem(text = "Edit chat", modifier = Modifier.padding(16.dp).clickable {

        }),
        PopupItem(text = "Members", modifier = Modifier.padding(16.dp).clickable {

        }),
        PopupItem(text = "Leave", modifier = Modifier.padding(16.dp).clickable {

        })
    )

    init {
        viewModelScope.launch {
            ChatDatabaseImpl().getChat(id) {
                chatName.value = it?.name ?: "..."
            }
        }
    }
}