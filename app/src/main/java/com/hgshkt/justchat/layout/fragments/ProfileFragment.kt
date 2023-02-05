package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var avatar: ImageView
    lateinit var inviteButton: ImageView
    lateinit var tvStatus: TextView
    lateinit var tvName: TextView
    lateinit var tvCustomId: TextView
    lateinit var tvBio: TextView
    lateinit var etName: EditText
    lateinit var etCustomId: EditText
    lateinit var etBio: EditText

    lateinit var db: UserDatabase
    lateinit var profileUser: User
    lateinit var id: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserId()
        init()
        setListeners()


        CoroutineScope(Dispatchers.IO).launch {
            loadUser(id)
            updateUI()
        }
    }

    private suspend fun loadUser(id: String) {
        val db = UserDatabaseImpl()
        profileUser = db.getUserById(id)
    }

    private fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            // TODO avatar
            // TODO inviteButton
            // TODO tvStatus
            tvName.text = profileUser.name
            tvCustomId.text = profileUser.id
            tvBio.text = profileUser.bio
        }
    }

    private fun setListeners() {
        inviteButton.setOnClickListener {
            inviteButtonClick()
        }
    }

    private fun inviteButtonClick() {
        // TODO
    }

    private fun getUserId() {
        val extraId = requireActivity().intent.getStringExtra("id")
        if (extraId == null) {
            runBlocking {
                id = CurrentUser.get()!!.firebaseId
            }
        } else {
            id = extraId
        }
    }

    private fun init() {
        avatar = requireView().findViewById(R.id.profile_avatar)
        inviteButton = requireView().findViewById(R.id.profile_button)
        tvStatus = requireView().findViewById(R.id.profile_status)
        tvName = requireView().findViewById(R.id.profile_name)
        tvCustomId = requireView().findViewById(R.id.profile_id)
        tvBio = requireView().findViewById(R.id.profile_bio)
        etName = requireView().findViewById(R.id.profile_et_name)
        etCustomId = requireView().findViewById(R.id.profile_et_id)
        etBio = requireView().findViewById(R.id.profile_et_bio)

        db = UserDatabaseImpl()
    }
}