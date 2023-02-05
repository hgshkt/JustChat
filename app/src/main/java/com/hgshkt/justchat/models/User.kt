package com.hgshkt.justchat.models

class User {
    var name: String = "name"
    var id: String = "id"
    var email: String = "email"
    var password: String = "password"
    var firebaseId: String = "firebaseId"
    var bio: String = "User didn't tell about self..."

    var chatIdList: List<String> = mutableListOf()
    var gottenInvites: List<String> = mutableListOf()
    var sentInvites: List<String> = mutableListOf()
    var friendList: List<String> = mutableListOf()


    constructor()

    constructor(
        name: String,
        id: String,
        email: String,
        password: String,
        firebaseId: String
    ) {
        this.name = name
        this.id = id
        this.email = email
        this.password = password
        this.firebaseId = firebaseId
    }
}