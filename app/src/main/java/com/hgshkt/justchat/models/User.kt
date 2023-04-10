package com.hgshkt.justchat.models

data class User(
    var name: String = "...",
    var id: String = "...",
    var email: String = "...",
    var password: String = "",
    var fid: String = "",
    var bio: String = "User didn't tell about self...",
    var avatarUri: String = "",
    var chatIdMap: MutableMap<String, String> = mutableMapOf(),
    var gottenInvites: MutableList<String> = mutableListOf(),
    var sentInvites: MutableList<String> = mutableListOf(),
    var friendList: MutableList<String> = mutableListOf()
)