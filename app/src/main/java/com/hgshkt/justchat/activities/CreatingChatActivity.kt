package com.hgshkt.justchat.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.creators.ChatCreator

class CreatingChatActivity : AppCompatActivity() {

    lateinit var rvFriends: RecyclerView
    lateinit var etChatName: EditText
    lateinit var button: Button

    lateinit var membersId: List<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_creating_chat)

        init()
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

    private fun init() {
        rvFriends = findViewById(R.id.creating_chat_friend_list)
        etChatName = findViewById(R.id.et_creating_chat)
        button = findViewById(R.id.creating_chat_button)

        membersId = mutableListOf()
    }
}