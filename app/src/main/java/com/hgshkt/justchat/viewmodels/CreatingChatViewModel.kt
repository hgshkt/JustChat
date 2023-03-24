package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User

class CreatingChatViewModel : ViewModel() {
    private val controller: UserController = UserController()
    private var idList: List<String> = mutableListOf()

    var chatName = mutableStateOf("")
        private set

    var userList = mutableStateListOf<User>()
        private set

    init {
        CurrentUser.addValueChangeListener {
            idList = it.friendList
            userList.clear()
            idList.forEach { fid ->
                val user = controller.getUserByFID(fid)!!
                userList.add(user)
            }
        }
    }

    fun click(user: User) {
        println("clicked user fid: ${user.fid}")
        TODO()
    }

    fun createChat() {
        println("creating chat: ${chatName.value}")
        TODO()
    }
}