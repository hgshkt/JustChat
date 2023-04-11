package com.hgshkt.justchat.ui.screens

import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.rememberImagePainter
import com.hgshkt.justchat.tools.loaders.uploadUserAvatar
import com.hgshkt.justchat.viewmodels.ProfileViewModel

@Composable
fun ProfileScreen(
    fid: String?,
    navController: NavController
) {
    val viewModel = remember {
        ProfileViewModel(fid, navController)
    }
    val user = viewModel.user.value

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            uploadUserAvatar(uri!!)
        }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column {
            Row(
                modifier = Modifier.padding(
                    top = 10.dp,
                    bottom = 10.dp,
                    start = 10.dp,
                    end = 20.dp
                )
            ) {
                Surface(
                    color = MaterialTheme.colors.surface,
                    elevation = 8.dp,
                    modifier = Modifier
                        .padding(16.dp)
                ) {
                    Image(
                        painter = rememberImagePainter(user.avatarUri),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(150.dp)
                            .clickable {
                                launcher.launch("image/*")
                            }
                    )
                }
                Column(
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 16.dp)
                ) {
                    Surface(
                        color = MaterialTheme.colors.surface,
                        elevation = 8.dp,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Column(
                            modifier = Modifier
                                .wrapContentHeight()
                                .padding(10.dp)
                        ) {
                            Text(
                                text = "name:",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = user.name,
                                style = TextStyle(
                                    color = Color.Black,
                                    fontSize = 16.sp
                                )
                            )
                            Text(
                                text = "id:",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 14.sp
                                )
                            )
                            Text(
                                text = "@${user.id}",
                                style = TextStyle(
                                    color = Color.Gray,
                                    fontSize = 16.sp
                                )
                            )
                        }
                    }
                    Box(
                        modifier = Modifier.padding(vertical = 12.dp)
                    ) {
                        ProfileButton(viewModel)
                    }
                }
            }
            Box(
                modifier = Modifier
                    .padding(
                        start = 16.dp,
                        end = 16.dp,
                        bottom = 16.dp
                    )
            ) {
                Surface(
                    color = MaterialTheme.colors.surface,
                    elevation = 8.dp,
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight()
                ) {
                    Text(
                        text = user.bio,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun ProfileButton(viewModel: ProfileViewModel) {
    val status = viewModel.status.value
    Surface(
        color = MaterialTheme.colors.surface,
        elevation = 8.dp,
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .clickable {
                viewModel.buttonClick()
            }
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier
                .padding(vertical = 4.dp, horizontal = 14.dp)
        ) {
            Text(
                text = status.buttonText
            )
            Image(
                imageVector = status.icon,
                contentDescription = null
            )
        }
    }
}