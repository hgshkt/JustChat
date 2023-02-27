package com.hgshkt.justchat.models

import java.util.*

class Message {
    lateinit var text: String
    lateinit var authorFid: String

    var id: String = UUID.randomUUID().toString()
    var date: Long = System.currentTimeMillis()

    constructor() {}

    constructor(
        text: String,
        authorFid: String
    ) {
        this.text = text
        this.authorFid = authorFid
    }
}