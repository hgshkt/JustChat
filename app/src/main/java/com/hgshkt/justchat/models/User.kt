package com.hgshkt.justchat.models

class User {
    var name: String = "name"
    var id: String = "id"
    var email: String = "email"
    var password: String = "password"

    constructor() {}

    constructor(
        name: String,
        id: String,
        email: String,
        password: String,
    ) {
        this.name = name
        this.id = id
        this.email = email
        this.password = password
    }



}