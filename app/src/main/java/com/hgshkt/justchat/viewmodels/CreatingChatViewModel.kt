package com.hgshkt.justchat.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.auth.currentUserFID
import com.hgshkt.justchat.auth.onCurrentUserChange
import com.hgshkt.justchat.creators.ChatCreator
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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

    var avatarUri = mutableStateOf<Uri?>(null)

    init {
        onCurrentUserChange {
            idList = it.friendList
            userList.clear()
            idList.forEach { fid ->
                viewModelScope.launch {
                    val user = dao.getUserByFID(fid)!!
                    userList.add(user)
                }
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
        CoroutineScope(Dispatchers.IO).launch {
            membersFID.add(currentUserFID!!)
            val chat = ChatCreator().createChat(
                chatName = chatName.value,
                avatarUri = avatarUri.value,
                membersFID = membersFID
            )
            withContext(Dispatchers.Main) {
                navController.navigate(Screen.ChatScreen.withArg("id", chat.id))
            }
        }
    }
}