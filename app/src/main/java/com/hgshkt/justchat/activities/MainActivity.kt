package com.hgshkt.justchat.activities

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.hgshkt.justchat.R
import com.hgshkt.justchat.adapters.ChatListAdapter
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.database.ChatDatabase
import com.hgshkt.justchat.database.ChatDatabaseImpl
import com.hgshkt.justchat.models.Chat
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    lateinit var recyclerView: RecyclerView

    lateinit var chatIdList: List<String>
    lateinit var chatList: List<Chat>

    lateinit var db: ChatDatabase

    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        init()

        if (auth.currentUser == null) {
            Intent(this@MainActivity, LoginActivity::class.java).also {
                startActivity(it)
            }
        } else {
            CoroutineScope(Dispatchers.IO).launch {
                updateUI()
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.plusButton -> openCreatingChatActivity()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun openCreatingChatActivity() {
        val intent = Intent(this@MainActivity, CreatingChatActivity::class.java)
        startActivity(intent)
    }

    private fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView.setHasFixedSize(true)

            updateAdapter()
        }
    }

    private suspend fun updateAdapter() {
        val user = CurrentUser.get()
        chatIdList = user!!.chatIdList
        chatList = db.getChatList(chatIdList)
        recyclerView.adapter = ChatListAdapter(
            context = this@MainActivity,
            chatList = chatList
        )
    }

    private fun init() {
        recyclerView = findViewById(R.id.main_rv)

        db = ChatDatabaseImpl()
        auth = FirebaseAuth.getInstance()
    }
}