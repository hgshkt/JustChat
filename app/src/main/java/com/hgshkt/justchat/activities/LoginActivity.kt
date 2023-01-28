package com.hgshkt.justchat.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class LoginActivity : AppCompatActivity() {

    lateinit var loginButton: Button
    lateinit var etRegistration: TextView

    lateinit var etEmail: EditText
    lateinit var etPassword: EditText

    lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        init()
        setListeners()
    }

    private fun login() {
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        if(email.isNotEmpty() && password.isNotEmpty()) {
            CoroutineScope(Dispatchers.IO).launch {
                try{
                    auth.signInWithEmailAndPassword(email, password)
                } catch (exception: Exception) {
                    withContext(Dispatchers.Main) {
                        Toast.makeText(this@LoginActivity, "Error", Toast.LENGTH_LONG).show()
                    }
                }
            }
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
        loginButton = findViewById(R.id.loginButton)
        etRegistration = findViewById(R.id.loginRegistrationButton)

        etEmail = findViewById(R.id.etEmail)
        etPassword = findViewById(R.id.etPassword)

        auth = FirebaseAuth.getInstance()
    }
}