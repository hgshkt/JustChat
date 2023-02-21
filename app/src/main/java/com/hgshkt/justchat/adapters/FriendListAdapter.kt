package com.hgshkt.justchat.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hgshkt.justchat.R
import com.hgshkt.justchat.controllers.UserController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendListAdapter(
    private val context: Context,
    private val friendFirebaseIdList: List<String>
) : RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    private val userController = UserController()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_friend, parent, false)

        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            val fid = friendFirebaseIdList[position]
            val friend = userController.getUserByFirebaseId(fid)!!

            launch(Dispatchers.Main) {
                holder.tvName.text = friend.name
                Glide.with(context)
                    .load(friend.avatarUri)
                    .into(holder.avatar)
            }
        }
    }

    override fun getItemCount() = friendFirebaseIdList.size

    inner class FriendViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var avatar: ImageView
        var tvName: TextView
        var checkBox: CheckBox

        init {
            tvName = view.findViewById(R.id.item_friend_name)
            checkBox = view.findViewById(R.id.item_friend_checkbox)
            avatar = view.findViewById(R.id.item_friend_avatar)
        }
    }
}