package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.creators.ChatCreator

class CreatingChatFragment : Fragment() {
    lateinit var rvFriends: RecyclerView
    lateinit var etChatName: EditText
    lateinit var button: Button

    lateinit var membersId: List<String>

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

        setListeners()
    }

    private fun setListeners() {
        button.setOnClickListener {
            createChat()
        }
    }

    private fun createChat() {
        val chatName = etChatName.text.toString()
        val chatCreator = ChatCreator()
        chatCreator.createChat(chatName, membersId)
    }

    private fun init(root: View) {
        rvFriends = root.findViewById(R.id.creating_chat_friend_list)
        etChatName = root.findViewById(R.id.et_creating_chat)
        button = root.findViewById(R.id.creating_chat_button)

        membersId = mutableListOf()
    }
}