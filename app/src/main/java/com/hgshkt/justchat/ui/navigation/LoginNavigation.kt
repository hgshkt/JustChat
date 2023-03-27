package com.hgshkt.justchat.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hgshkt.justchat.ui.screens.LoginScreen
import com.hgshkt.justchat.ui.screens.RegistrationScreen

@Composable
fun LoginNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = Screen.LoginScreen.route
    ) {
        composable(route = Screen.LoginScreen.route) {
            LoginScreen(navController)
        }
        composable(route = Screen.RegistrationScreen.route) {
            RegistrationScreen(navController)
        }
    }
}