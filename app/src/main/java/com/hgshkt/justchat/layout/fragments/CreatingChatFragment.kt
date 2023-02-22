package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.adapters.FriendListAdapter
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.creators.ChatCreator
import kotlinx.coroutines.*

class CreatingChatFragment : Fragment() {
    lateinit var recyclerView: RecyclerView
    lateinit var etChatName: EditText
    lateinit var button: Button

    lateinit var membersFID: List<String>
    lateinit var friendFIDList: List<String>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_creating_chat, container, false)

        init(root)

        return root
    }

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Default).launch {
            val async = async { CurrentUser.get()!!.friendList }

            setListeners()
            bindRecyclerView(async)
        }
    }

    private suspend fun bindRecyclerView(async: Deferred<List<String>>) {
        CoroutineScope(Dispatchers.Default).launch {
            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            val adapter = FriendListAdapter(
                requireContext(),
                async.await(),
                findNavController(),
                true
            )
            adapter.addMembersList(membersFID)
            launch(Dispatchers.Main) {
                recyclerView.adapter = adapter
            }
        }
    }

    private fun setListeners() {
        button.setOnClickListener {
            createChat()
        }
    }

    private fun createChat() {
        val chatName = etChatName.text.toString()
        val chatCreator = ChatCreator()
        chatCreator.createChat(chatName, membersFID)
    }

    private fun init(root: View) {
        recyclerView = root.findViewById(R.id.creating_chat_friend_list)
        etChatName = root.findViewById(R.id.et_creating_chat)
        button = root.findViewById(R.id.creating_chat_button)

        membersFID = mutableListOf()
    }
}