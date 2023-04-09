package com.hgshkt.justchat.models

import java.util.*

data class Message (
    var text: String = "",
    var authorFid: String = "",
    var id: String = UUID.randomUUID().toString(),
    var time: String = System.currentTimeMillis().toString(),
)