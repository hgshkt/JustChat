package com.hgshkt.justchat.ui.navigation

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hgshkt.justchat.auth.signOut
import com.hgshkt.justchat.layout.activities.LoginActivity

@Composable
fun SignOutButton(
    context: Context
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                signOut()
                val intent = Intent(context, LoginActivity::class.java)
                context.startActivity(intent)
            }
            .padding(16.dp)
    ) {
        Icon(
            imageVector = Icons.Default.ExitToApp,
            contentDescription = "Sign out nutton"
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text = "Sign out",
            style = TextStyle(fontSize = 18.sp),
            modifier = Modifier.weight(1f)
        )
    }
}