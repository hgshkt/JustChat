package com.hgshkt.justchat.models

import java.util.*

class Message {
    var id: String
    var text: String
    var authorFid: String
    var date: Long

    constructor(
        text: String,
        authorFid: String,
        date: Long
    ) {
        this.text = text
        this.authorFid = authorFid
        this.date = date

        id = UUID.randomUUID().toString()
    }
}