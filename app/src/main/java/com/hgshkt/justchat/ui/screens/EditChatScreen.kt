package com.hgshkt.justchat.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.*
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
import com.hgshkt.justchat.viewmodels.EditChatViewModel

@Composable
fun EditChatScreen(
    id: String,
    navController: NavController
) {
    val viewModel = remember { EditChatViewModel(id, navController) }
    val name = viewModel.chatName
    val avatarUri = viewModel.avatarUri.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            viewModel.avatarUri.value = uri!!
        }

    val constraints = ConstraintSet {
        val avatar = createRefFor("avatar")
        val textField = createRefFor("textField")
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
                painter = if (avatarUri != null)
                    rememberImagePainter(avatarUri)
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
            value = name.value,
            onValueChange = {
                name.value = it
            },
            modifier = Modifier.layoutId("textField"),
            placeholder = {
                Text("Chat name")
            }
        )
        Button(
            modifier = Modifier.layoutId("button"),
            onClick = {
                viewModel.saveChat()
            }) {
            Text("Save")
        }
    }
}