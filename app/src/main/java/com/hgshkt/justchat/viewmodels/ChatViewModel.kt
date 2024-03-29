package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.hgshkt.justchat.tools.auth.currentUserFID
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.MessageDao
import com.hgshkt.justchat.tools.managers.ChatManager
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.launch

class ChatViewModel(
    private val id: String
) : ViewModel() {
    private var chatDao: ChatDao = ChatDao()
    private var messageDao: MessageDao = MessageDao()
    private lateinit var manager: ChatManager

    private var chatState: MutableState<Chat> = mutableStateOf(Chat())
    var messages = mutableStateListOf<Message>()
    var messageText = mutableStateOf("")

    init {
        viewModelScope.launch {
            chatDao.getChat(id) {
                chatState.value = it!!
                manager = ChatManager(chatState.value)
            }
        }
    }

    fun sendMessage() {
        val message = Message(
            text = messageText.value,
            authorFid = currentUserFID!!
        )
        manager.sendMessage(message)
        messageText.value = ""
    }

    fun fetchMessagesFromFirebase(messages: MutableList<Message>) {
        viewModelScope.launch {
            chatDao.addChatMessagesChangeListener(id, object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val message = messageDao.getMessage(snapshot.value.toString())
                    message.let { messages.add(it) }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val message = messageDao.getMessage(snapshot.value.toString())
                    message.let { messages.remove(it) }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onCancelled(error: DatabaseError) {

                }
            })
        }
    }
}