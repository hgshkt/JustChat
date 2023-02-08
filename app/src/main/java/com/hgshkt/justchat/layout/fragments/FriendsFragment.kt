package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import com.hgshkt.justchat.R
import com.hgshkt.justchat.controllers.UserController
import com.hgshkt.justchat.database.UserDatabase
import com.hgshkt.justchat.database.UserDatabaseImpl
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    lateinit var etSearch: EditText
    lateinit var searchButton: ImageView

    private lateinit var controller: UserController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        init()
        setListeners()
    }

    private fun setListeners() {
        searchButton.setOnClickListener {
            searchById()
        }
    }

    private fun searchById() {
        val searchId = etSearch.text.toString()

        if (controller.getUserById(searchId) == null) {
            CoroutineScope(Dispatchers.Main).launch {
                Toast.makeText(
                    requireContext(),
                    "User not exists",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            requireActivity().supportFragmentManager.commit {
                val bundle = Bundle()
                bundle.putString("id", searchId)
                val profile = ProfileFragment()
                profile.arguments = bundle
                replace(R.id.nav_host_fragment, profile)
            }
        }
    }


    private fun init() {
        etSearch = requireView().findViewById(R.id.friend_et_search)
        searchButton = requireView().findViewById(R.id.friend_search_button)

        controller = UserController()
    }
}