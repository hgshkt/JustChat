package com.hgshkt.justchat.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.navigation.NavController
import com.hgshkt.justchat.dao.database.ChatDatabaseImpl
import com.hgshkt.justchat.tools.managers.ChatManager
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.ui.items.PopupItem
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

@Composable
fun ChatBar(
    navController: NavController,
    onNavigationIconClick: () -> Unit,
    id: String
) {
    val viewModel = remember { ChatBarViewModel(navController, id) }
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
                        Box(
                            modifier = viewModel.popupItems[it].modifier.fillMaxSize()
                        ) {
                            Text(
                                text = viewModel.popupItems[it].text,
                                modifier = Modifier.padding(16.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}

private class ChatBarViewModel(
    val navController: NavController,
    val id: String
) : ViewModel() {
    var chat = Chat()
    val chatName = mutableStateOf("")
    val popupItems = mutableListOf(
        PopupItem(text = "Edit chat", modifier = Modifier.clickable {
            openEditChat()
        }),
        PopupItem(text = "Members", modifier = Modifier.clickable {
            openMembersScreen()
        }),
        PopupItem(text = "Leave", modifier = Modifier.clickable {
            leave()
        })
    )

    init {
        viewModelScope.launch {
            ChatDatabaseImpl().getChat(id) {
                chat = it ?: Chat()
                chatName.value = it?.name ?: "..."
            }
        }
    }


    private fun openEditChat() {
        navController.navigate(Screen.EditChatScreen.withArg("id", id))
    }
    private fun openMembersScreen() {
        navController.navigate(Screen.ChatMembersScreen.withArg("id", id))
    }

    private fun leave() {
        val manager = ChatManager(chat)
        manager.leave()

        navController.navigate(Screen.ChatListScreen.route)
    }
}