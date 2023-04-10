package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.currentUserFID
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val navController: NavController
) : ViewModel() {
    private val userDao: UserDao = UserDao()

    private val chatIdMap = mutableStateMapOf<String, String>()
    var chatList = mutableListOf<String>()

    init {
        viewModelScope.launch {
            userDao.observeChatMap(currentUserFID!!) {
                it.forEach { (id, time) ->
                    chatIdMap[id] = time
                }
                chatList = chatIdMap.toList()
                    .sortedBy { (_, value) -> value }
                    .map { (key, _) -> key }
                    .toMutableList()
            }
        }
    }

    fun openChat(
        chatId: String
    ) {
        navController.navigate(Screen.ChatScreen.withArg("id", chatId))
    }
}