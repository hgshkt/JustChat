package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.adapters.ChatListAdapter
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.ChatController
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ChatListFragment : Fragment(R.layout.fragment_chat_list) {
    lateinit var recyclerView: RecyclerView

    lateinit var chatIdMap: HashMap<String, String>
    lateinit var chatIdList: List<String>
    lateinit var chatList: List<Chat>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onStart() {
        super.onStart()
        updateUI()
    }

    private fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            recyclerView.setHasFixedSize(true)

            updateAdapter()
        }
    }

    private suspend fun updateAdapter() {
        withContext(Dispatchers.IO) {
            CurrentUser.get()
        }.also {
            if (it == null) return

            chatIdMap = it.chatIdMap
            chatIdList = mapToValueList(chatIdMap)
            chatList = ChatController().getChatList(chatIdList)

            recyclerView.adapter = ChatListAdapter(
                context = requireContext(),
                chatList = chatList
            )
        }
    }

    private fun <K, V>mapToValueList(map: HashMap<K, V>): List<V> {
        val list: List<V> = mutableListOf()
        for (value in map.values) {
            (list as MutableList).add(value)
        }
        return list
    }

    private fun init() {
        recyclerView = requireView().findViewById(R.id.chat_list_rv)
    }
}