package com.hgshkt.justchat.viewmodels

import android.net.Uri
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.hgshkt.justchat.dao.ChatDao
import com.hgshkt.justchat.loaders.uploadChatAvatar
import com.hgshkt.justchat.models.Chat
import com.hgshkt.justchat.ui.navigation.Screen
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EditChatViewModel(
    val id: String,
    val navController: NavController
) : ViewModel() {
    private var chat: Chat = Chat()
    private val chatDao: ChatDao = ChatDao()

    var avatarUri = mutableStateOf<Uri?>(null)
    var chatName = mutableStateOf("")

    init {
        viewModelScope.launch {
            chatDao.getChat(id) {
                chat = it ?: Chat()
                avatarUri.value = Uri.parse(it?.avatarUri)
                chatName.value = it?.name ?: "..."
            }
        }
    }

    fun saveChat() {
        chat.name = chatName.value
        uploadChatAvatar(
            chatId = chat.id,
            uri = avatarUri.value ?: Uri.parse(chat.avatarUri)
        )
        CoroutineScope(Dispatchers.IO).launch {
            chatDao.updateChat(chat)
            withContext(Dispatchers.Main) {
                navController.navigate(Screen.ChatScreen.withArg("id", chat.id))
            }
        }
    }
}