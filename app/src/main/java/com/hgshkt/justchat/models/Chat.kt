package com.hgshkt.justchat.models

import java.util.*

class Chat {
    lateinit var name: String
    lateinit var membersFid: List<String>

    var id: String = UUID.randomUUID().toString()
    var lastMessageTime: String = System.currentTimeMillis().toString()
    var messagesId: List<String> = mutableListOf()

    constructor() {}

    constructor(
        name: String,
        membersFid: List<String>
    ) {
        this.name = name
        this.membersFid = membersFid
    }
}