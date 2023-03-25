package com.hgshkt.justchat.managers

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.hgshkt.justchat.adapters.MessagesAdapter
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.MessageDao
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

    private lateinit var adapter: MessagesAdapter
    private lateinit var messageList: List<Message>

    private val messageDao = MessageDao()
    private val chatDao = ChatDao()

    init {
        runBlocking(Dispatchers.IO) {
            updateMessageList()
            updateAdapter()

            chatDao.addMessagesChangedListener(chat.id, object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    chat.messagesHashMap = snapshot.getValue<HashMap<String, String>>() ?: hashMapOf()
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
        messageDao.create(message)
        chatDao.addMessageToChat(
            chatId = chat.id,
            messageId = message.id,
            time = message.date.toString()
        )
        chatDao.updateChatLastMessageTime(chat.id, message.date.toString())
    }

    private fun updateMessageList() {
        val sortedMap = chat.messagesHashMap.toSortedMap()
        val messagesId = mapToValueList(sortedMap)
        messageList = messageDao.getMessages(messagesId)
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