package com.example.singleactivityexample.screens.users

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.observeSafe
import com.example.singleactivityexample.screens.users.adapter.UserItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class UsersFragment : Fragment(R.layout.fragment_users), FlexibleAdapter.OnItemClickListener {

    private val adapter = FlexibleAdapter(emptyList(), this)
    private val viewModel by viewModel<UsersViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter

        viewModel.usersLiveData.observeSafe(viewLifecycleOwner) { state ->
            when(state) {
                is UsersViewModel.UsersScreenState.UsersState -> {
                    adapter.updateDataSet(state.users.map { UserItem(it) })
                }
            }
        }
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return false
    }
}
