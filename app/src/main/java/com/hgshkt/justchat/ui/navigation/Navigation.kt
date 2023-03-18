package com.hgshkt.justchat.ui.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun Navigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.ChatListScreen.route
    ) {
        composable(route = Screen.ChatListScreen.route) {
            ChatListScreen()
        }
        composable(route = Screen.CreatingChatScreen.route) {
            CreatingChatScreen()
        }
        composable(route = Screen.FriendListScreen.route) {
            FriendListScreen()
        }
        composable(route = Screen.ProfileScreen.route) {
            ProfileScreen()
        }
    }
}

@Composable
fun ChatListScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "Chat list screen")
    }
}

@Composable
fun CreatingChatScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "Creating chat screen")
    }
}

@Composable
fun FriendListScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "Friend list screen")
    }
}

@Composable
fun ProfileScreen() {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Text(text = "Profile screen")
    }
}