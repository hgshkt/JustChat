package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.Message
import com.hgshkt.justchat.models.User

class MessageViewModel(private val message: Message): ViewModel() {

    private val userDao: UserDao = UserDao()
    var user = mutableStateOf(User())

    init {
        userDao.addOnValueChangeListener(message.authorFid) {
            user.value = it.getValue(User::class.java)!!
        }
    }
}