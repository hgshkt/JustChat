package com.hgshkt.justchat.layout.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.adapters.ChatListAdapter
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.ChatController
import com.hgshkt.justchat.layout.activities.LoginActivity
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {
    lateinit var recyclerView: RecyclerView

    lateinit var chatIdList: List<String>
    lateinit var chatList: List<Chat>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onStart() {
        super.onStart()

        val auth = AppAuth()
        if (!auth.entered) {
            auth.signOut()
            Intent(requireContext(), LoginActivity::class.java).also {
                startActivity(it)
            }
        } else updateUI()
    }

    private fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)

            updateAdapter()
        }
    }

    private suspend fun updateAdapter() {
        val user = CurrentUser.get()
        chatIdList = user!!.chatIdList
        chatList = ChatController().getChatListByIdList(chatIdList)

        recyclerView.adapter = ChatListAdapter(
            context = requireContext(),
            chatList = chatList
        )
    }

    private fun init() {
        recyclerView = requireView().findViewById(R.id.chats_rv)
    }
}