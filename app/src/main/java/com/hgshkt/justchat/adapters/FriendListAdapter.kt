package com.hgshkt.justchat.adapters

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.hgshkt.justchat.R
import com.hgshkt.justchat.controllers.UserController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendListAdapter(
    private val context: Context,
    private val friendFirebaseIdList: List<String>,
    private val navController: NavController,
    private val withCheckBox: Boolean
) : RecyclerView.Adapter<FriendListAdapter.FriendViewHolder>() {

    private val userController = UserController()
    private var membersFID : List<String>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {
        val view = LayoutInflater
            .from(context)
            .inflate(R.layout.item_friend, parent, false)

        return FriendViewHolder(view)
    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        CoroutineScope(Dispatchers.Default).launch {
            val fid = friendFirebaseIdList[position]
            val friend = userController.getUserByFID(fid)!!

            launch(Dispatchers.Main) {
                holder.tvName.text = friend.name
                if (withCheckBox) {
                    holder.checkBox.visibility = View.VISIBLE
                }
                Glide.with(context)
                    .load(friend.avatarUri)
                    .into(holder.avatar)

                holder.itemView.setOnClickListener {
                    val bundle = Bundle()
                    bundle.putString("id", friend.fid)
                    navController.navigate(R.id.friend_to_profile, bundle)
                }

                holder.checkBox.setOnCheckedChangeListener { button, isChecked ->
                    if (isChecked) {
                        (membersFID as MutableList<String>).add(friend.fid)
                    } else {
                        (membersFID as MutableList<String>).remove(friend.fid)
                    }
                }
            }
        }
    }

    override fun getItemCount() = friendFirebaseIdList.size

    fun addMembersList(membersFID: List<String>) {
        this.membersFID = membersFID
    }

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