package com.hgshkt.justchat.ui.navigation

sealed class Screen(val route: String) {
    object ChatListScreen : Screen("chat_list_screen")
    object CreatingChatScreen : Screen("creating_chat_screen")
    object FriendListScreen : Screen("friend_list_screen")
    object ProfileScreen : Screen("profile_screen")

    object LoginScreen : Screen("login_screen")

    object RegistrationScreen : Screen("registration_screen")

    fun withArg(
        argName: String,
        argValue: String
    ): String {
        return buildString {
            append(route)
            append("?$argName=")
            append(argValue)
        }
    }
}
