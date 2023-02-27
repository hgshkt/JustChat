package com.hgshkt.justchat.managers

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.hgshkt.justchat.adapters.MessagesAdapter
import com.hgshkt.justchat.controllers.ChatController
import com.hgshkt.justchat.controllers.MessageController
import com.hgshkt.justchat.mapToValueList
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ChatManager(
    var chat: Chat,
    val context: Context,
    val recyclerView: RecyclerView
) {

    lateinit var adapter: MessagesAdapter
    private lateinit var messageList: List<Message>

    private var messageNumber = 0
    private val messageNumberIncrease = 10
    private val messageController = MessageController()
    private val chatController = ChatController()

    init {
        runBlocking(Dispatchers.IO) {
            loadMessages()
            updateAdapter()

            chatController.addListener(chat.id, object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chat = snapshot.getValue<Chat>()!!
                    val messagesId = mapToValueList(chat.messagesId)

                    messageList = messageController.getMessages(messagesId)

                    updateAdapter()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("ChatManager", "loadPost:onCancelled", error.toException())
                }
            })
        }
    }

    fun sendMessage(message: Message) {
        messageController.create(message)
        chatController.addMessageToChat(
            chatId = chat.id,
            messageId = message.id,
            time = message.date.toString()
        )
    }

    private suspend fun loadMessages() {
        messageNumber += messageNumberIncrease
        messageList = mutableListOf()
        if (chat.messagesId.isEmpty()) return

        val messagesId = chat.messagesId.toSortedMap()
        val keys = messagesId.keys.toMutableList()
        for (i in 0 until messageNumber) {
            if (keys.size < messageNumber - 1 && i >= keys.size) break

            val time = keys[i]
            val id = messagesId[time]
            val message = messageController.getMessage(id!!)
            (messageList as MutableList).add(message)
        }
    }

    private fun updateAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            adapter = MessagesAdapter(context, messageList)
            recyclerView.adapter = adapter
        }
    }
}