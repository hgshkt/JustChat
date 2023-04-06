package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.creators.ChatCreator
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen

class CreatingChatViewModel(
    private val navController: NavController
) : ViewModel() {
    private val dao: UserDao = UserDao()
    private var idList: List<String> = mutableListOf()
    private val membersFID: MutableList<String> = mutableListOf()

    var chatName = mutableStateOf("")
        private set

    var userList = mutableStateListOf<User>()
        private set

    init {
        CurrentUser.addValueChangeListener {
            idList = it.friendList
            userList.clear()
            idList.forEach { fid ->
                val user = dao.getUserByFID(fid)!!
                userList.add(user)
            }
        }
    }

    fun invite(user: User, invited: Boolean) {
        if (invited)
            membersFID.add(user.fid)
        else
            membersFID.remove(user.fid)
    }

    fun createChat() {
        membersFID.add(AppAuth().currentUserFID!!)
        val chat = ChatCreator().createChat(
            chatName = chatName.value,
            membersFID = membersFID
        )
        navController.navigate(Screen.ChatScreen.withArg("id", chat.id))
    }
}