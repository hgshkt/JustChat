package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.ProfileStatus
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.launch

class ProfileViewModel(private val fid: String?) : ViewModel() {

    val user = mutableStateOf(User())
    val status = mutableStateOf(ProfileStatus.DEFAULT)

    val buttonIcon = status.value.icon
    val buttonText = status.value.buttonText

    private val dao: UserDao = UserDao()

    init {
        if (fid == null)
            CurrentUser.addValueChangeListener {
                user.value = it
            }
        else
            dao.addOnValueChangeListener(fid) {
                user.value = it.getValue(User::class.java)!!
            }

        viewModelScope.launch {
            status.value = ProfileStatus.getProfileStatusFor(fid!!)
        }
    }

    fun buttonClick() {
        viewModelScope.launch {
            status.value.buttonClick(fid!!)
        }
    }
}