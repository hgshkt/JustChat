package com.hgshkt.justchat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navArgument
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.ui.screens.*

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChatListScreen.route
    ) {
        composable(route = Screen.ChatListScreen.route) {
            ChatListScreen(navController)
        }
        composable(route = Screen.CreatingChatScreen.route) {
            CreatingChatScreen()
        }
        composable(route = Screen.FriendListScreen.route) {
            FriendListScreen()
        }
        composable(
            route = Screen.ProfileScreen.route,
            arguments = listOf(
                navArgument("userFID") {
                    type = NavType.StringType
                    defaultValue = AppAuth().currentUserFID
                    nullable = true
                }
            )
        ) {
            ProfileScreen(it.arguments?.getString("userFID"))
        }
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen()
        }
    }
}

