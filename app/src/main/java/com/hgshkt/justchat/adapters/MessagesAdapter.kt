package com.hgshkt.justchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hgshkt.justchat.R
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Message
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MessagesAdapter(
    private val context: Context,
    var messageList: List<Message>
) : RecyclerView.Adapter<MessagesAdapter.MessageViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MessageViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_message, parent, false)
        return MessageViewHolder(view)
    }

    override fun onBindViewHolder(holder: MessageViewHolder, position: Int) {
        val message = messageList[position]

        CoroutineScope(Dispatchers.IO).launch {
            val user = UserDao().getUserByFID(message.authorFid)
            withContext(Dispatchers.Main) {
                val userName = user?.name ?: "name"
                holder.tvName.text = userName
            }
            withContext(Dispatchers.Main){
                if (user == null) {
                    Glide
                        .with(context)
                        .load(R.drawable.ic_default_avatar)
                        .into(holder.avatar)
                } else {
                    Glide
                        .with(context)
                        .load(user.avatarUri)
                        .into(holder.avatar)
                }
            }
        }
        CoroutineScope(Dispatchers.Main).launch {
            holder.tvData.text = message.text
        }
    }

    override fun getItemCount(): Int {
        return messageList.size
    }

    inner class MessageViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var avatar: ImageView
        var tvName: TextView
        var tvData: TextView

        init {
            avatar = itemView.findViewById(R.id.item_message_avatar)
            tvName = itemView.findViewById(R.id.item_message_username)
            tvData = itemView.findViewById(R.id.item_message_data)
        }
    }
}