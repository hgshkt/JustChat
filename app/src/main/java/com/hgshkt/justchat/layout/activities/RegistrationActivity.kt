package com.hgshkt.justchat.layout.activities

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.AppAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

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
        val name = etName.text.toString()
        val id = etId.text.toString()
        val email = etEmail.text.toString()
        val password = etPassword.text.toString()

        CoroutineScope(Dispatchers.IO).launch {
            auth.createUserWithEmailAndPassword(email, password).await()
            auth.signInWithEmailAndPassword(email, password).await()
            val appAuth = AppAuth()
            Log.i("taggg", "${auth.currentUser}")
            appAuth.createUser(
                name = name,
                customId = id,
                email = email,
                password = password,
                firebaseId = auth.currentUser!!.uid
            )

            Intent(this@RegistrationActivity, MainActivity::class.java).also {
                startActivity(it)
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