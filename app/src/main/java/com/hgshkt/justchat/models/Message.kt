package com.hgshkt.justchat.models

import java.util.*

class Message {
    var text: String? = null
    var authorFid: String? = null

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