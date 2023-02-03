package com.hgshkt.justchat.layout.fragments

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.R
import com.hgshkt.justchat.layout.activities.LoginActivity
import com.hgshkt.justchat.adapters.ChatListAdapter
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatListFragment : Fragment(R.layout.fragment_chats){
    lateinit var recyclerView: RecyclerView

    lateinit var chatIdList: List<String>
    lateinit var chatList: List<Chat>

    lateinit var db: ChatDatabase

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
    }

    override fun onStart() {
        super.onStart()

        if (auth.currentUser == null) {
            Intent(requireContext(), LoginActivity::class.java).also {
                startActivity(it)
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                updateUI()
            }
        }
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
        chatList = db.getChatList(chatIdList)
        recyclerView.adapter = ChatListAdapter(
            context = requireContext(),
            chatList = chatList
        )
    }

    private fun init() {
        recyclerView = view!!.findViewById(R.id.chats_rv)

        db = ChatDatabaseImpl()
        auth = FirebaseAuth.getInstance()
    }
}