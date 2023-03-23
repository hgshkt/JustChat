package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.ChatController
import com.hgshkt.justchat.models.Chat

class ChatListViewModel : ViewModel() {

    var idList by mutableStateOf(emptyList<String>())
    var chatList by mutableStateOf(emptyList<Chat>())

    init {
        CurrentUser.addValueChangeListener {
            idList = it.chatIdMap.values.toList()
            chatList = loadChats(idList)
        }
    }


    fun loadChats(idList: List<String>): List<Chat> {
        val chats = mutableListOf<Chat>()
        idList.forEach {
            val chat = ChatController().getChat(it)!!
            chats.add(chat)
        }
        return chats
    }
}