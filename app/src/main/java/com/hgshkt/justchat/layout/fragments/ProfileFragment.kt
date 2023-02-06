package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.FriendController
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
    lateinit var controller: FriendController
    lateinit var profileUser: User
    lateinit var id: String
    private lateinit var status: Status

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        getUserId()
        init()
        updateStatus()
        setListeners()

        CoroutineScope(Dispatchers.IO).launch {
            loadUser()
            updateUI()
        }
    }

    private suspend fun loadUser() {
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

    private fun updateStatus() {
        runBlocking {
            val currentUserId = CurrentUser.get()!!.firebaseId

            Status.values().forEach {
                if (it.condition(id, currentUserId))
                    status = it
            }
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
        controller = FriendController()
    }

    private enum class Status {
        CURRENT {
            override fun condition(profileId: String, currentUserId: String): Boolean {
                return profileId == currentUserId
            }
        },
        FRIEND {
            override fun condition(profileId: String, currentUserId: String): Boolean =
                runBlocking {
                    val controller = FriendController()
                    controller.areFriends(profileId, currentUserId)
                }
        },
        SENDER {
            override fun condition(profileId: String, currentUserId: String): Boolean =
                runBlocking {
                    val controller = FriendController()
                    val profileHasCurrent = controller.idInSentInviteList(profileId, currentUserId)
                    val curHasProfile = controller.idInGottenInviteList(profileId, currentUserId)
                    profileHasCurrent && curHasProfile
                }
        },
        RECIPIENT {
            override fun condition(profileId: String, currentUserId: String): Boolean =
                runBlocking {
                    val controller = FriendController()
                    val profileHasCurrent =
                        controller.idInGottenInviteList(profileId, currentUserId)
                    val curHasProfile = controller.idInSentInviteList(profileId, currentUserId)
                    profileHasCurrent && curHasProfile
                }
        },
        DEFAULT {
            override fun condition(profileId: String, currentUserId: String): Boolean {
                for (status in Status.values()) {
                    if (status.condition(
                            profileId,
                            currentUserId
                        ) && status != DEFAULT
                    ) return false
                }
                return true
            }
        };

        abstract fun condition(profileId: String, currentUserId: String): Boolean
    }
}