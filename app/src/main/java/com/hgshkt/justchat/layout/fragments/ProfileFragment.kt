package com.hgshkt.justchat.layout.fragments

import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.hgshkt.justchat.R
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.FriendController
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.models.User
import kotlinx.coroutines.*

class ProfileFragment : Fragment(R.layout.fragment_profile) {

    lateinit var avatar: ImageView
    lateinit var inviteButton: ImageView
    lateinit var imgLoadButton: ImageView
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

        CoroutineScope(Dispatchers.Default).launch {
            init()
            loadId()
            updateStatus()
            setListeners()

            CoroutineScope(Dispatchers.IO).launch {
                loadUsers()
                updateUI()
            }
        }
    }


    private fun loadUsers() {
        profileUser = userController.getUserByFirebaseId(profileFirebaseId)!!
    }

    private fun updateUI() {
        CoroutineScope(Dispatchers.Main).launch {
            // TODO avatar
            tvName.text = profileUser.name
            tvCustomId.text = profileUser.id
            tvBio.text = profileUser.bio

            updateStatusDrawing(status)
        }
    }

    private suspend fun setListeners() {
        withContext(Dispatchers.Main) {
            inviteButton.setOnClickListener {
                inviteButtonClick()
            }
        }
    }

    private fun inviteButtonClick() {
        CoroutineScope(Dispatchers.IO).launch {
            when (status) {
                Status.FRIEND -> friendController.stopFriendship(
                    CurrentUser.get()!!,
                    profileUser
                )
                Status.SENDER -> friendController.acceptInvite(
                    profileUser,
                    CurrentUser.get()!!
                )
                Status.CURRENT -> TODO()
                Status.RECIPIENT -> friendController.cancelInviting(
                    CurrentUser.get()!!,
                    profileUser
                )
                Status.DEFAULT -> friendController.sendInvite(
                    CurrentUser.get()!!,
                    profileUser
                )
            }
            updateStatus()
            updateStatusDrawing(status)
        }
    }

    private fun updateStatusDrawing(status: Status) {
        CoroutineScope(Dispatchers.Main).launch {
            Glide.with(this@ProfileFragment)
                .load(status.id)
                .into(inviteButton)

            tvStatus.text = status.text

            if (status == Status.CURRENT) {
                imgLoadButton.visibility = View.VISIBLE
            } else {
                imgLoadButton.visibility = View.INVISIBLE
            }
        }
    }

    private fun loadId() {
        runBlocking {
            currentUserFirebaseId = CurrentUser.get()!!.firebaseId
            if (arguments != null && requireArguments().containsKey("id")) {
                val id = requireArguments().getString("id")!!
                val user = userController.getUserById(id)
                profileFirebaseId = user!!.firebaseId
            } else {
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
        imgLoadButton = requireView().findViewById(R.id.profile_img)
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
            override val id: Int = R.drawable.ic_settings
            override val text: String = "This is your profile"

            override fun condition(profileId: String, currentUserId: String): Boolean {
                return profileId == currentUserId
            }
        },
        FRIEND {
            override val id: Int = R.drawable.ic_friends
            override val text: String = "This is your friend"

            override fun condition(profileId: String, currentUserId: String): Boolean =
                runBlocking {
                    val controller = FriendController()
                    controller.areFriends(profileId, currentUserId)
                }
        },
        SENDER {
            override val id: Int = R.drawable.ic_accept_invite
            override val text: String = "This user sent invite"

            override fun condition(profileId: String, currentUserId: String): Boolean =
                runBlocking {
                    val controller = FriendController()
                    val profileHasCurrent = controller.idInSentInviteList(profileId, currentUserId)
                    val curHasProfile = controller.idInGottenInviteList(profileId, currentUserId)
                    profileHasCurrent && curHasProfile
                }
        },
        RECIPIENT {
            override val id: Int = R.drawable.ic_invite_sent
            override val text: String = "You are sent invite to this user"

            override fun condition(profileId: String, currentUserId: String): Boolean =
                runBlocking {
                    val controller = FriendController()
                    val profileHasCur = controller.idInGottenInviteList(currentUserId, profileId)
                    val curHasProfile = controller.idInSentInviteList(currentUserId, profileId)
                    profileHasCur && curHasProfile
                }
        },
        DEFAULT {
            override val id: Int = R.drawable.ic_add_friend
            override val text: String = "Send invite"

            override fun condition(profileId: String, currentUserId: String): Boolean {
                for (status in Status.values()) {
                    if (status != DEFAULT && status.condition(profileId, currentUserId)) {
                        return false
                    }
                }
                return true
            }
        };

        abstract val id: Int
        abstract val text: String

        abstract fun condition(profileId: String, currentUserId: String): Boolean
    }
}