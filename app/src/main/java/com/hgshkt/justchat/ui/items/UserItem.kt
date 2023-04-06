package com.hgshkt.justchat.ui.items

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.hgshkt.justchat.models.User

@Composable
fun UserItem(
    user: User,
    onClickEvent: (user: User) -> Unit = {}
) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .clickable {
            onClickEvent(user)
        }) {
        Row(
            modifier = Modifier.padding(10.dp)
        ) {
            Image(
                painter = rememberImagePainter(user.avatarUri),
                contentDescription = null,
                modifier = Modifier
                    .height(50.dp)
                    .width(50.dp)
            )
            Column(
                verticalArrangement = Arrangement.Center,
                modifier = Modifier
                    .padding(start = 12.dp)
            ) {
                Text(
                    text = user.name,
                    style = TextStyle(
                        color = Color.Black,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "@${user.id}",
                    style = TextStyle(
                        color = Color.Gray,
                        fontSize = 18.sp
                    )
                )
            }
        }
    }
}