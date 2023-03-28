package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Checkbox
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hgshkt.justchat.models.User
import com.hgshkt.justchat.ui.items.UserItem
import com.hgshkt.justchat.viewmodels.CreatingChatViewModel

@Composable
fun CreatingChatScreen() {
    val viewModel = remember { CreatingChatViewModel() }
    val chatName = viewModel.chatName

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            contentAlignment = Alignment.Center,
        ) {
            Text(
                text = "Create chat", style = TextStyle(
                    fontSize = 26.sp,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        TextField(
            modifier = Modifier
                .fillMaxWidth(),
            placeholder = { Text(text = "Chat name") },
            value = chatName.value,
            onValueChange = {
                chatName.value = it
            })
        Spacer(modifier = Modifier.height(16.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.8f)
        ) {
            FriendList(viewModel) { user, checked ->
                viewModel.invite(user, checked)
            }
        }
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(16.dp)
        ) {
            Button(onClick = { viewModel.createChat() }) {
                Text(text = "Create")
            }
        }
    }
}

/**
 * Composable fun to show current user's friend list
 * @param event is event that occurs after clicking on UserItem
 */
@Composable
private fun FriendList(
    viewModel: CreatingChatViewModel,
    event: (user: User, checked: Boolean) -> Unit
) {
    val userList = viewModel.userList

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        items(userList.size) {
            val checkedState = remember {
                mutableStateOf(false)
            }
            Row {
                UserItem(user = userList[it]) { user ->
                    checkedState.value = !checkedState.value
                    event(user, checkedState.value)
                }
                Box(
                    modifier = Modifier.padding(10.dp)
                ) {
                    Checkbox(
                        checked = checkedState.value,
                        onCheckedChange = { checked ->
                            checkedState.value = checked
                            event(userList[it], checked)
                        }
                    )
                }
            }
        }
    }
}