package com.hgshkt.justchat.models

import java.util.*
import kotlin.collections.HashMap

data class Chat(
    var name: String = "...",
    var avatarUri: String = "",
    var lastMessageAuthorFid: String = "",
    var membersFid: MutableList<String> = mutableListOf(),
    var messagesHashMap: HashMap<String, String> = hashMapOf(),
    var id: String = UUID.randomUUID().toString(),
    var lastMessageTime: String = System.currentTimeMillis().toString()
)