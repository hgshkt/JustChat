package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.launch

class ChatListViewModel : ViewModel() {
    private val userDao: UserDao = UserDao()
    private val chatDao: ChatDao = ChatDao()

    var chatList = mutableStateListOf<Chat>()

    fun observeChatList() {
        viewModelScope.launch {
            userDao.addChatListChangeListener(object : ChildEventListener {
                override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                    val chatId = snapshot.value.toString()
                    viewModelScope.launch {
                        val chat = chatDao.getChat(chatId)
                        chatList.add(0, chat!!)
                    }
                }

                override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {

                }

                override fun onChildRemoved(snapshot: DataSnapshot) {
                    val chatId = snapshot.value.toString()
                    chatList.removeIf {
                        it.id == chatId
                    }
                }

                override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                    TODO("Not yet implemented")
                }

                override fun onCancelled(error: DatabaseError) {
                    TODO("Not yet implemented")
                }
            })
        }
    }

    fun openChat(
        navController: NavController,
        chat: Chat
    ) {
        navController.navigate(Screen.ChatScreen.withArg("id", chat.id))
    }
}