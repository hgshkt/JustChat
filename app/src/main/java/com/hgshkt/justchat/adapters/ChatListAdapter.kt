package com.hgshkt.justchat.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.models.Chat

class ChatListAdapter(
    private val context: Context,
    private val chatList: List<Chat>,
    private val navController: NavController
) : RecyclerView.Adapter<ChatListAdapter.ChatViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        val chat = chatList[position]
        holder.chatName.text = chat.name

        holder.chatName.setOnClickListener {
            openChat(chat)
        }
    }

    private fun openChat(chat: Chat) {
        val bundle = Bundle()
        bundle.putString("id", chat.id)
        navController.navigate(R.id.chat_list_to_chat, bundle)
    }

    override fun getItemCount(): Int {
        return chatList.size
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var chatName: TextView

        init {
            chatName = itemView.findViewById(R.id.item_chat_name)
        }
    }
}