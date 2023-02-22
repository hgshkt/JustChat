package com.hgshkt.justchat.openers

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.hgshkt.justchat.R
import com.hgshkt.justchat.layout.fragments.ProfileFragment

class ProfileOpener(
    val id: String? = null,
    private val fragment: ProfileFragment
) {

    private val ID_KEY = "id"

    fun open() {
        if (id == null) {
            openCurrent()
        } else {
            openWithId()
        }
    }

    private fun openCurrent() {
        fragment.findNavController().navigate(R.id.friend_to_profile)
    }

    private fun openWithId() {
        val bundle = Bundle()
        bundle.putString(ID_KEY, id)
        fragment.findNavController().navigate(R.id.friend_to_profile, bundle)
    }
}