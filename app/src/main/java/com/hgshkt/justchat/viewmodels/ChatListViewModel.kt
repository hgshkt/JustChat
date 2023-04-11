package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.tools.auth.currentUserFID
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class ChatListViewModel(
    private val navController: NavController
) : ViewModel() {
    private val userDao: UserDao = UserDao()

    private val chatIdMap = mutableStateMapOf<String, String>()
    val chatList = mutableStateListOf<String>()

    init {
        observeChatMap()
    }

    fun observeChatMap() {
        viewModelScope.launch {
            userDao.observeChatMap(currentUserFID!!) {
                it.forEach { (id, time) ->
                    chatIdMap[id] = time
                }
                chatList.clear()
                chatList.addAll(chatIdMap.toList()
                    .sortedByDescending { (_, value) -> value }
                    .map { (key, _) -> key }
                )
                println(chatList.toMutableList())
            }
        }
    }

    fun openChat(chatId: String) {
        navController.navigate(Screen.ChatScreen.withArg("id", chatId))
    }
}