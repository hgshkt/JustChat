package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.hgshkt.justchat.ui.theme.ButtonBack
import com.hgshkt.justchat.viewmodels.RegistrationViewModel

@Composable
fun RegistrationScreen(
    navController: NavController
) {
    val viewModel = remember { RegistrationViewModel(navController) }
    val name = viewModel.name.value
    val id = viewModel.id.value
    val email = viewModel.email.value
    val password = viewModel.password.value

    val constraints = ConstraintSet {
        val label = createRefFor("label")
        val nameField = createRefFor("nameField")
        val idField = createRefFor("idField")
        val emailField = createRefFor("emailField")
        val passwordField = createRefFor("passwordField")
        val registrationButton = createRefFor("registrationButton")
        val sendEmailButton = createRefFor("sendEmailButton")

        constrain(label) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(parent.top, 50.dp)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
        constrain(nameField) {
            start.linkTo(parent.start, 50.dp)
            end.linkTo(parent.end, 50.dp)
            top.linkTo(label.bottom, 20.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(idField) {
            start.linkTo(nameField.start)
            end.linkTo(nameField.end)
            top.linkTo(nameField.bottom, 20.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(emailField) {
            start.linkTo(idField.start)
            end.linkTo(idField.end)
            top.linkTo(idField.bottom, 20.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(passwordField) {
            start.linkTo(emailField.start)
            end.linkTo(emailField.end)
            top.linkTo(emailField.bottom, 20.dp)

            width = Dimension.fillToConstraints
            height = Dimension.wrapContent
        }
        constrain(registrationButton) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(passwordField.bottom, 20.dp)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
        constrain(sendEmailButton) {
            start.linkTo(parent.start)
            end.linkTo(parent.end)
            top.linkTo(registrationButton.bottom, 8.dp)

            width = Dimension.wrapContent
            height = Dimension.wrapContent
        }
    }

    ConstraintLayout(constraintSet = constraints, modifier = Modifier.fillMaxSize()) {
        Text(
            modifier = Modifier.layoutId("label"),
            text = "Registration",
            style = TextStyle(
                fontSize = 34.sp
            )
        )
        TextField(
            modifier = Modifier
                .layoutId("nameField"),
            placeholder = { Text(text = "Name") },
            value = name,
            onValueChange = {
                viewModel.name.value = it
            }
        )
        TextField(
            modifier = Modifier
                .layoutId("idField"),
            placeholder = { Text(text = "Id") },
            value = id,
            onValueChange = {
                viewModel.id.value = it
            }
        )
        TextField(
            modifier = Modifier
                .layoutId("emailField"),
            placeholder = { Text(text = "Email") },
            value = email,
            onValueChange = {
                viewModel.email.value = it
            }
        )
        TextField(
            modifier = Modifier
                .layoutId("passwordField"),
            placeholder = { Text(text = "Password") },
            value = password,
            onValueChange = {
                viewModel.password.value = it
            }
        )
        Button(
            modifier = Modifier
                .layoutId("registrationButton"),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonBack
            ),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                viewModel.registration()
            }
        ) {
            Text(
                text = "Registration",
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
        Button(
            modifier = Modifier
                .layoutId("sendEmailButton"),
            colors = ButtonDefaults.buttonColors(
                backgroundColor = ButtonBack
            ),
            shape = RoundedCornerShape(20.dp),
            onClick = {
                viewModel.sendEmail()
            }
        ) {
            Text(
                text = "Send email",
                style = TextStyle(
                    fontSize = 16.sp
                )
            )
        }
    }
}