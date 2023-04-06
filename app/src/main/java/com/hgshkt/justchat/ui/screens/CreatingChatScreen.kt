package com.hgshkt.justchat.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.hgshkt.justchat.R
import com.hgshkt.justchat.ui.items.UserItem
import com.hgshkt.justchat.viewmodels.CreatingChatViewModel

@Composable
fun CreatingChatScreen(
    navController: NavController
) {
    val viewModel = remember { CreatingChatViewModel(navController) }
    val chatAvatar = viewModel.avatarUri
    val chatName = viewModel.chatName
    val userList = viewModel.userList

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.avatarUri.value = uri!!
        }

    val constraints = ConstraintSet {
        val avatar = createRefFor("avatar")
        val textField = createRefFor("textField")
        val lazyColumn = createRefFor("lazyColumn")
        val button = createRefFor("button")

        constrain(avatar) {
            start.linkTo(parent.start, 30.dp)
            end.linkTo(textField.start)
            top.linkTo(parent.top, 30.dp)

            width = Dimension.value(100.dp)
            height = Dimension.value(100.dp)
        }
        constrain(textField) {
            top.linkTo(avatar.top)
            end.linkTo(parent.end, 30.dp)
            start.linkTo(avatar.end, 16.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(lazyColumn) {
            top.linkTo(avatar.bottom, 16.dp)
            bottom.linkTo(button.top, 18.dp)
            start.linkTo(parent.start, 20.dp)
            end.linkTo(parent.end, 20.dp)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
        constrain(button) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            bottom.linkTo(parent.bottom, 20.dp)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }

    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        Surface(
            color = MaterialTheme.colors.surface,
            elevation = 10.dp,
            modifier = Modifier.layoutId("avatar")
        ) {
            Image(
                painter = if (chatAvatar.value != null)
                    rememberImagePainter(chatAvatar.value)
                else
                    painterResource(id = R.drawable.ic_image),
                contentDescription = "chat avatar",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .clickable {
                        launcher.launch("image/*")
                    }
            )
        }
        TextField(
            value = chatName.value,
            onValueChange = { chatName.value = it },
            modifier = Modifier.layoutId("textField"),
            placeholder = { Text(text = "Chat name") }
        )
        LazyColumn(
            modifier = Modifier
                .layoutId("lazyColumn")
        ) {
            items(userList.size) {
                val checkedState = remember {
                    mutableStateOf(false)
                }
                Row {
                    UserItem(user = userList[it]) { user ->
                        checkedState.value = !checkedState.value
                        viewModel.invite(user, checkedState.value)
                    }
                    Box(
                        modifier = Modifier.padding(10.dp)
                    ) {
                        Checkbox(
                            checked = checkedState.value,
                            onCheckedChange = { checked ->
                                checkedState.value = checked
                                viewModel.invite(userList[it], checked)
                            }
                        )
                    }
                }
            }
        }
        Button(
            modifier = Modifier.layoutId("button"),
            onClick = { viewModel.createChat() }) {
            Text("Create")
        }
    }
}