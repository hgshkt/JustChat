package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ChatItemViewModel(
    chatId: String
): ViewModel() {

    private val userDao: UserDao = UserDao()
    private val chatDao: ChatDao = ChatDao()

    var author = mutableStateOf(User())
    var chat = mutableStateOf(Chat())

    init {
        viewModelScope.launch(Dispatchers.IO) {
            chatDao.getChat(chatId) {
                chat.value = it!!

                if (it.lastMessageAuthorFid.isNotEmpty()) {
                    viewModelScope.launch(Dispatchers.IO) {
                        author.value = userDao.getUserByFID(it.lastMessageAuthorFid)!!
                    }
                }
            }
        }
    }
}