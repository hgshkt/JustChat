package com.hgshkt.justchat.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.ChatAuth
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrationActivity : AppCompatActivity() {

    lateinit var etName: EditText
    lateinit var etId: EditText
    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    lateinit var registrationButton: Button

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        init()
        setListeners()
    }

    private fun setListeners() {
        registrationButton.setOnClickListener {
            registration()
        }
    }

    private fun registration() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            try {
                val chatAuth = ChatAuth()
                val user = User(
                    name = "test name",
                    id = "test id",
                    email = email,
                    password = password
                )
                chatAuth.createUser(user, this@RegistrationActivity)
            } catch (exception: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(this@RegistrationActivity, "Error", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    private fun init() {
        etName = findViewById(R.id.registration_et_name)
        etId = findViewById(R.id.registration_et_id)
        etEmail = findViewById(R.id.registration_et_email)
        etPassword = findViewById(R.id.registration_et_password)

        registrationButton = findViewById(R.id.registration_button)

        auth = FirebaseAuth.getInstance()
    }
}