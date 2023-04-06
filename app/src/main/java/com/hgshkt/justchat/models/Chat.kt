package com.hgshkt.justchat.models

import java.util.*
import kotlin.collections.HashMap

class Chat {
    var name: String = "..."
    var membersFid: List<String> = mutableListOf()
    var avatarUri: String = ""

    var id: String = UUID.randomUUID().toString()
    var lastMessageTime: String = System.currentTimeMillis().toString()

    /**
     * key is message date time in milliseconds
     * value is message id
     */
    var messagesHashMap: HashMap<String, String> = hashMapOf()

    constructor() {}

    constructor(
        name: String,
        membersFid: List<String>
    ) {
        this.name = name
        this.membersFid = membersFid
    }
}