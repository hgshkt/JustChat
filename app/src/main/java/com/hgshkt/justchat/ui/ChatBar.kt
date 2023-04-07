package com.hgshkt.justchat.ui

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgshkt.justchat.database.ChatDatabaseImpl
import kotlinx.coroutines.launch

@Composable
fun ChatBar(
    onNavigationIconClick: () -> Unit,
    id: String
) {
    val viewModel = remember { ChatBarViewModel(id) }
    TopAppBar(
        title = {
            Text(text = viewModel.chatName.value)
        },
        backgroundColor = MaterialTheme.colors.primary,
        contentColor = MaterialTheme.colors.onPrimary,
        navigationIcon = {
            IconButton(onClick = onNavigationIconClick) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Toggle drawer"
                )
            }
        }
    )
}

private class ChatBarViewModel(
    id: String
) : ViewModel() {
    val chatName = mutableStateOf("")

    init {
        viewModelScope.launch {
            ChatDatabaseImpl().getChat(id) {
                chatName.value = it?.name ?: "..."
            }
        }
    }
}