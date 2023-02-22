package com.hgshkt.justchat.layout.fragments

import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hgshkt.justchat.R
import com.hgshkt.justchat.adapters.FriendListAdapter
import com.hgshkt.justchat.auth.CurrentUser
import com.hgshkt.justchat.controllers.UserController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FriendsFragment : Fragment(R.layout.fragment_friends) {
    lateinit var etSearch: EditText
    lateinit var searchButton: ImageView
    lateinit var recyclerView: RecyclerView

    private lateinit var controller: UserController

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        CoroutineScope(Dispatchers.Default).launch {
            init()
            bindRecyclerView()
            setListeners()
        }
    }

    private fun bindRecyclerView() {
        CoroutineScope(Dispatchers.IO).launch {
            val friendFIDList = async { CurrentUser.get()!!.friendList }

            recyclerView.setHasFixedSize(true)
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            CoroutineScope(Dispatchers.Main).launch {
                recyclerView.adapter = FriendListAdapter(
                    requireContext(),
                    friendFIDList.await(),
                    findNavController(),
                    false
                )
            }
        }
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
            val bundle = Bundle()
            bundle.putString("id", searchId)
            findNavController().navigate(R.id.friend_to_profile, bundle)
        }
    }


    private fun init() {
        etSearch = requireView().findViewById(R.id.friend_et_search)
        searchButton = requireView().findViewById(R.id.friend_search_button)
        recyclerView = requireView().findViewById(R.id.rv_friends)

        controller = UserController()
    }
}