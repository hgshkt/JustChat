package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.ChatController
import com.hgshkt.justchat.managers.ChatManager
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class ChatFragment : Fragment(R.layout.fragment_chat) {

    lateinit var recyclerView: RecyclerView
    lateinit var editText: EditText
    lateinit var sendButton: ImageView

    lateinit var currentChat: Chat
    lateinit var manager: ChatManager

    private lateinit var j: Job

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        j = CoroutineScope(Dispatchers.IO).launch {
            loadChat()
        }
        init()
        setListeners()
        updateUI()
    }

    private fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)

            j.join()

            manager = ChatManager(currentChat, requireContext(), recyclerView)
        }
    }

    private suspend fun loadChat() {
        val id = requireArguments().getString("id")!!
        val chat = ChatController().getChat(id)
        if (chat == null) {
            // TODO to chat list
        } else {
            currentChat = chat
        }
    }

    private fun sendMessage() {
        val text = editText.text.toString()
        val authorFID = CurrentUser.get()?.fid ?: return

        editText.text.clear()

        val message = Message(text = text, authorFid = authorFID)
        manager.sendMessage(message)
    }

    private fun setListeners() {
        sendButton.setOnClickListener {
            sendMessage()
        }
    }

    private fun init() {
        recyclerView = requireView().findViewById(R.id.chat_rv)
        editText = requireView().findViewById(R.id.chat_et)
        sendButton = requireView().findViewById(R.id.chat_send)
    }

}