package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.hgshkt.justchat.viewmodels.EditProfileViewModel

@Composable
fun EditProfileScreen(
    fid: String,
    navController: NavController
) {
    val viewModel = remember { EditProfileViewModel(fid, navController) }
    val name = viewModel.name
    val id = viewModel.id
    val bio = viewModel.bio

    val constraints = ConstraintSet {
        val nameField = createRefFor("name")
        val idField = createRefFor("id")
        val bioField = createRefFor("bio")
        val button = createRefFor("button")

        constrain(nameField) {
            start.linkTo(parent.start, 10.dp)
            top.linkTo(parent.top, 10.dp)
            end.linkTo(parent.end, 10.dp)

            width = Dimension.fillToConstraints
        }
        constrain(idField) {
            start.linkTo(parent.start, 10.dp)
            top.linkTo(nameField.bottom, 10.dp)
            end.linkTo(parent.end, 10.dp)

            width = Dimension.fillToConstraints
        }
        constrain(bioField) {
            start.linkTo(parent.start, 10.dp)
            end.linkTo(parent.end, 10.dp)
            top.linkTo(idField.bottom, 10.dp)
            bottom.linkTo(button.top, 10.dp)

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
        TextField(
            value = name.value,
            onValueChange = { name.value = it },
            modifier = Modifier.layoutId("name"),
            placeholder = { Text(text = "Name") }
        )
        TextField(
            value = id.value,
            onValueChange = { id.value = it },
            modifier = Modifier.layoutId("id"),
            placeholder = { Text(text = "Id") }
        )
        TextField(
            value = bio.value,
            onValueChange = { bio.value = it },
            modifier = Modifier.layoutId("bio"),
            placeholder = { Text(text = "Bio") }
        )
        Button(
            modifier = Modifier.layoutId("button"),
            onClick = {
                viewModel.save()
            }) {
            Text("Save")
        }
    }
}