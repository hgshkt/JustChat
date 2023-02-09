package com.hgshkt.justchat.layout.fragments

import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.FriendController
import com.hgshkt.justchat.controllers.UserController
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

    private lateinit var friendController: FriendController
    private lateinit var userController: UserController
    private lateinit var profileUser: User
    private lateinit var currentUserFirebaseId: String
    private lateinit var status: Status
    private lateinit var profileFirebaseId: String

    override fun onStart() {
        super.onStart()

        CoroutineScope(Dispatchers.Main).launch {
            init()
            loadId()
            updateStatus()
            setListeners()

            CoroutineScope(Dispatchers.IO).launch {
                loadUser()
                updateUI()
            }
        }
    }


    private fun loadUser() {
        profileUser = userController.getUserByFirebaseId(profileFirebaseId)!!
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
        when (status) {
            Status.FRIEND -> friendController.stopFriendship(currentUserFirebaseId, profileFirebaseId)
            Status.SENDER -> friendController.acceptInvite(profileFirebaseId, currentUserFirebaseId)
            Status.CURRENT -> TODO()
            Status.RECIPIENT -> friendController.cancelInviting(currentUserFirebaseId, profileFirebaseId)
            Status.DEFAULT -> friendController.sendInvite(currentUserFirebaseId, profileFirebaseId)
        }
    }

    private fun loadId() {
        runBlocking {
            currentUserFirebaseId = CurrentUser.get()!!.firebaseId
            if (arguments != null && requireArguments().containsKey("id")) {
                val id = requireArguments().getString("id")!!
                val user = userController.getUserById(id)
                profileFirebaseId = user!!.firebaseId
            }
            else {
                profileFirebaseId = currentUserFirebaseId
            }
        }
    }

    private fun updateStatus() {
        Status.values().forEach {
            if (it.condition(profileFirebaseId, currentUserFirebaseId)) {
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

        friendController = FriendController()
        userController = UserController()
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
                    if (status != DEFAULT && status.condition(profileId, currentUserId)) {
                        return false
                    }
                }
                return true
            }
        };

        abstract fun condition(profileId: String, currentUserId: String): Boolean
    }
}