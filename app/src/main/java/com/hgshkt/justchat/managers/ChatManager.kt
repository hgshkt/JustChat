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

    private val messageController = MessageController()
    private val chatController = ChatController()

    init {
        runBlocking(Dispatchers.IO) {
            updateMessageList()
            updateAdapter()
            chatController.addMessagesChangedListener(chat.id, object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chat.messagesId = snapshot.getValue<HashMap<String, String>>() ?: hashMapOf()
                    updateMessageList()
                    insertMessage()
                }

                override fun onCancelled(error: DatabaseError) {
                    Log.w("ChatManager", "cancelled", error.toException())
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

    private fun updateMessageList() {
        val sortedMap = chat.messagesId.toSortedMap()
        val messagesId = mapToValueList(sortedMap)
        messageList = messageController.getMessages(messagesId)
    }

    private fun updateAdapter() {
        CoroutineScope(Dispatchers.Main).launch {
            adapter = MessagesAdapter(context, messageList)
            recyclerView.adapter = adapter
        }
    }

    private fun insertMessage() {
        adapter.messageList = messageList
        recyclerView.adapter?.notifyDataSetChanged()
    }
}