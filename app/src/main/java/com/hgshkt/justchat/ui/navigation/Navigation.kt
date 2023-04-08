package com.hgshkt.justchat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.layout.activities.MainActivity
import com.hgshkt.justchat.ui.screens.*

@Composable
fun Navigation(
    navController: NavHostController,
    idState: MutableState<String>,
    screenType: MutableState<MainActivity.ScreenType>
) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChatListScreen.route
    ) {
        composable(route = Screen.ChatListScreen.route) {
            screenType.value = MainActivity.ScreenType.Main
            ChatListScreen(navController)
        }
        composable(route = Screen.CreatingChatScreen.route) {
            screenType.value = MainActivity.ScreenType.Main
            CreatingChatScreen(navController)
        }
        composable(route = Screen.FriendListScreen.route) {
            screenType.value = MainActivity.ScreenType.Main
            FriendListScreen(navController)
        }
        composable(route = Screen.SearchScreen.route) {
            screenType.value = MainActivity.ScreenType.Main
            SearchScreen(navController)
        }
        composable(
            route = Screen.ProfileScreen.route + "?userFID={userFID}",
            arguments = listOf(
                navArgument("userFID") {
                    type = NavType.StringType
                    defaultValue = AppAuth().currentUserFID
                    nullable = true
                }
            )
        ) {
            screenType.value = MainActivity.ScreenType.Main
            ProfileScreen(
                fid = it.arguments?.getString("userFID"),
                navController = navController
            )
        }
        composable(
            route = Screen.EditProfileScreen.route + "?userFID={userFID}",
            arguments = listOf(
                navArgument("userFID") {
                    type = NavType.StringType
                    defaultValue = AppAuth().currentUserFID
                    nullable = true
                }
            )
        ) {
            screenType.value = MainActivity.ScreenType.Main
            EditProfileScreen(
                fid = it.arguments?.getString("userFID") ?: AppAuth().currentUserFID!!,
                navController = navController
            )
        }
        composable(
            route = Screen.ChatScreen.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            screenType.value = MainActivity.ScreenType.Chat
            val id = it.arguments?.getString("id")!!
            idState.value = id
            ChatScreen(id = id)
        }
        composable(
            route = Screen.EditChatScreen.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            screenType.value = MainActivity.ScreenType.Main
            val id = it.arguments?.getString("id")!!
            idState.value = id
            EditChatScreen(id = id, navController = navController)
        }
        composable(
            route = Screen.ChatMembersScreen.route + "?id={id}",
            arguments = listOf(
                navArgument("id") {
                    type = NavType.StringType
                }
            )
        ) {
            screenType.value = MainActivity.ScreenType.Main
            val id = it.arguments?.getString("id")!!
            idState.value = id
            ChatMembersScreen(id = id, navController = navController)
        }
    }
}

