package com.hgshkt.justchat.layout.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.AppAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var etRegistration: TextView

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        CoroutineScope(Dispatchers.Default).launch {
            init()
            setListeners()
        }
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        AppAuth().login(email, password, this)

        Intent(this@LoginActivity, MainActivity::class.java).also {
            startActivity(it)
        }
    }

    private fun registration() {
        val intent = Intent(this, RegistrationActivity::class.java)
        startActivity(intent)
    }

    private fun setListeners() {
        loginButton.setOnClickListener {
            login()
        }
        etRegistration.setOnClickListener {
            registration()
        }
    }

    private fun init() {
        loginButton = findViewById(R.id.login_button)
        etRegistration = findViewById(R.id.login_registration_button)

        etEmail = findViewById(R.id.et_email)
        etPassword = findViewById(R.id.et_password)
    }
}