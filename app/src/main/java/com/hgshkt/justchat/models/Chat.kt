package com.hgshkt.justchat.models

import java.util.*

class Chat {
    lateinit var name: String
    lateinit var id: String
    lateinit var lastMessageTime: String
    lateinit var messages: List<Message>
    lateinit var membersId: List<String>

    constructor() {}

    constructor(
        name: String,
        membersId: List<String>
    ) {
        this.name = name
        this.membersId = membersId

        this.id = UUID.randomUUID().toString()
        this.messages = mutableListOf()
        this.lastMessageTime = System.currentTimeMillis().toString()
    }
}