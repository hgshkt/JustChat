package com.hgshkt.justchat.models

class Message {
    var text: String
    var author: String
    var date: Long

    constructor(
        text: String,
        author: String,
        date: Long
    ) {
        this.text = text
        this.author = author
        this.date = date
    }
}