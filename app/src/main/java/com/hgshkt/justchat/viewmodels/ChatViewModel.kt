package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.MessageDao
import com.hgshkt.justchat.managers.ChatManager
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatViewModel(
    private val id: String
) : ViewModel() {
    private var chatDao: ChatDao = ChatDao()
    private var messageDao: MessageDao = MessageDao()
    private var chatState: MutableState<Chat> = mutableStateOf(Chat())
    private var currentUserFID = AppAuth().currentUserFID
    var messages = mutableStateListOf<Message>()
    var messageText = mutableStateOf("")

    init {
        CoroutineScope(Dispatchers.IO).launch {
            chatDao.getChat(id) {
                chatState.value = it!! // TODO() chat can be nullable
                loadMessages()
            }
        }
    }

    private fun loadMessages() {
        messages.clear()
        val map = chatState.value.messagesHashMap.toSortedMap()
        map.values.forEach {
            messages.add(messageDao.getMessage(it))
        }
    }

    fun sendMessage() {
        val message = Message(messageText.value, currentUserFID!!)
        ChatManager(id).sendMessage(message)
        messageText.value = ""
    }
}