package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.hgshkt.justchat.models.User

@Composable
fun UserItem(
    user: User,
    onClickEvent: (user: User) -> Unit = {}
) {
    Column(
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 50.dp)
            .clickable {
                onClickEvent(user)
            }
    ) {
        Text(text = "name: ${user.name}")
        Text(text = "id: ${user.id}")
        Spacer(modifier = Modifier.height(100.dp))
    }
}