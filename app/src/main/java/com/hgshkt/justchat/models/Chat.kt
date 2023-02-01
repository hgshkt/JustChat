package com.hgshkt.justchat.models

class Chat {
    var name: String
    var id: String
    var messages: List<Message>

    constructor(
        name: String,
        id: String,
        messages: List<Message>
    ) {
        this.name = name
        this.id = id
        this.messages = messages
    }
}