package com.hgshkt.justchat.viewmodels

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.controllers.ProfileStatus
import com.hgshkt.justchat.dao.UserDao
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.launch

class ProfileViewModel(private var fid: String?) : ViewModel() {

    val user = mutableStateOf(User())
    val status = mutableStateOf(ProfileStatus.DEFAULT)

    private val dao: UserDao = UserDao()

    init {
        dao.addOnValueChangeListener(fid ?: AppAuth().currentUserFID!!) {
            user.value = it.getValue(User::class.java)!!

            viewModelScope.launch {
                status.value = ProfileStatus.getProfileStatusFor(fid!!)
            }
        }
    }

    fun buttonClick() {
        viewModelScope.launch {
            status.value.buttonClick(fid!!)
        }
    }
}