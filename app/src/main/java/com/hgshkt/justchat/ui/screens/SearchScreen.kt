package com.hgshkt.justchat.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintSet
import androidx.constraintlayout.compose.Dimension
import androidx.constraintlayout.compose.layoutId
import androidx.navigation.NavController
import com.hgshkt.justchat.viewmodels.SearchViewModel

@Composable
fun SearchScreen(
    navController: NavController
) {
    val viewModel = remember {
        SearchViewModel(navController)
    }
    val text = remember {
        viewModel.text
    }

    val constraints = ConstraintSet {
        val textField = createRefFor("textField")
        val searchIcon = createRefFor("search")

        constrain(searchIcon) {
            width = Dimension.value(50.dp)
            height = width
            end.linkTo(parent.end)
            top.linkTo(parent.top)
            start.linkTo(textField.end)
        }
        constrain(textField) {
            start.linkTo(parent.start)
            top.linkTo(searchIcon.top)
            end.linkTo(searchIcon.start)
            bottom.linkTo(searchIcon.bottom)

            width = Dimension.fillToConstraints
            height = Dimension.fillToConstraints
        }
    }

    ConstraintLayout(constraints, modifier = Modifier.fillMaxSize()) {
        TextField(
            placeholder = { Text("User id") },
            value = text.value, onValueChange = {
                text.value = it
            },
            modifier = Modifier.layoutId("textField")
        )
        Icon(
            imageVector = Icons.Default.Search,
            contentDescription = "search",
            modifier = Modifier.layoutId("search")
                .clickable {
                    viewModel.openProfile()
                }
        )
    }
}