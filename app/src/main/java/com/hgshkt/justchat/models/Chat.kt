package com.hgshkt.justchat.models

import java.util.*

class Chat {
    lateinit var name: String
    lateinit var id: String
    lateinit var lastMessageTime: String
    lateinit var messagesId: List<String>
    lateinit var membersFid: List<String>

    constructor() {}

    constructor(
        name: String,
        membersFid: List<String>
    ) {
        this.name = name
        this.membersFid = membersFid

        this.id = UUID.randomUUID().toString()
        this.messagesId = mutableListOf()
        this.lastMessageTime = System.currentTimeMillis().toString()
    }
}