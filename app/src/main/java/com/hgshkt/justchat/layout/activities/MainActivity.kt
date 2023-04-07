package com.hgshkt.justchat.layout.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.rememberNavController
import com.hgshkt.justchat.auth.AppAuth
import com.hgshkt.justchat.ui.navigation.AppBar
import com.hgshkt.justchat.ui.navigation.DrawerBody
import com.hgshkt.justchat.ui.navigation.MenuItem
import com.hgshkt.justchat.ui.navigation.Navigation
import com.hgshkt.justchat.ui.navigation.Screen
import com.hgshkt.justchat.ui.navigation.SignOutButton
import com.hgshkt.justchat.ui.theme.NavigationDrawerComposeTheme
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    @SuppressLint("UnusedMaterialScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NavigationDrawerComposeTheme {
                val navController = rememberNavController()
                val scaffoldState = rememberScaffoldState()
                val scope = rememberCoroutineScope()
                val auth = AppAuth()
                val isLoggedIn = remember {
                    mutableStateOf(
                        auth.entered && auth.emailVerified
                    )
                }

                if (isLoggedIn.value) {
                    Scaffold(
                        scaffoldState = scaffoldState,
                        topBar = {
                            AppBar(
                                onNavigationIconClick = {
                                    scope.launch {
                                        scaffoldState.drawerState.open()
                                    }
                                }
                            )
                        },
                        drawerGesturesEnabled = scaffoldState.drawerState.isOpen,
                        drawerContent = {
                            DrawerBody(
                                items = listOf(
                                    MenuItem(
                                        id = Screen.ChatListScreen.route,
                                        title = "Chats",
                                        contentDescription = "Go to chat list screen",
                                        icon = Icons.Default.Email
                                    ),
                                    MenuItem(
                                        id = Screen.CreatingChatScreen.route,
                                        title = "Create chat",
                                        contentDescription = "Go to creating chat screen",
                                        icon = Icons.Default.Add
                                    ),
                                    MenuItem(
                                        id = Screen.FriendListScreen.route,
                                        title = "Friends",
                                        contentDescription = "Go to friend list screen",
                                        icon = Icons.Default.Person
                                    ),
                                    MenuItem(
                                        id = Screen.SearchScreen.route,
                                        title = "Search friends",
                                        contentDescription = "Go to search screen",
                                        icon = Icons.Default.Search
                                    ),
                                    MenuItem(
                                        id = Screen.ProfileScreen.route,
                                        title = "Profile",
                                        contentDescription = "Go to profile screen",
                                        icon = Icons.Default.AccountBox
                                    )
                                ),
                                onItemClick = {
                                    navController.navigate(it.id)
                                    scope.launch {
                                        scaffoldState.drawerState.close()
                                    }
                                }
                            )
                            SignOutButton(navController.context)
                        }
                    ) {
                        Navigation(navController = navController)
                    }
                } else {
                    val intent = Intent(applicationContext, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
        }

    }
}