package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.models.Chat

class ChatListViewModel : ViewModel() {
    private val controller: ChatDao = ChatDao()
    private var idList: List<String> = mutableListOf()

    var chatList = mutableStateListOf<Chat>()

    init {
        CurrentUser.addValueChangeListener {
            idList = it.chatIdMap.values.toList()
            chatList.clear()
            idList.forEach {fid ->
                val chat = controller.getChat(fid)!!
                chatList.add(chat)
            }
        }
    }

    fun openChat(
        navController: NavController,
        chat: Chat
    ) {
        /*
         * TODO() navController.navigate(Screen.ChatScreen.route)
         *
         */
    }
}