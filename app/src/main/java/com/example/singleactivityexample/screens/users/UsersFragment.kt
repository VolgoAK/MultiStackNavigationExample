package com.example.singleactivityexample.screens.users

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.extraNotNull
import com.example.singleactivityexample.extensions.getExtraNotNull
import com.example.singleactivityexample.extensions.observeSafe
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.navigation.UserScreen
import com.example.singleactivityexample.screens.users.adapter.UserItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_users.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class UsersFragment : Fragment(R.layout.fragment_users), FlexibleAdapter.OnItemClickListener {

    companion object {
        private const val EXTRA_SCOPE = "extra_scope"
        fun newInstance(scopeName: String) = UsersFragment().apply {
            Timber.d("TESTING New instance UsersFragment")
            arguments = Bundle().apply {
                putString(EXTRA_SCOPE, scopeName)
            }
        }
    }

    private val adapter = FlexibleAdapter(emptyList(), this)
    private val viewModel by viewModel<UsersViewModel>()

    private val scope by lazy {
        val scopeName = getExtraNotNull<String>(EXTRA_SCOPE)
        getKoin().getScope(scopeName)
    }
    private val navigator by lazy { scope.get<Navigator>() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Timber.d("TESTING users fragment onCreate")
        /*if(savedInstanceState == null) {
            loadKoinModules(containerModule)
        }*/

    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        rvUsers.layoutManager = LinearLayoutManager(requireContext())
        rvUsers.adapter = adapter

        viewModel.usersLiveData.observeSafe(viewLifecycleOwner) { state ->
            when (state) {
                is UsersViewModel.UsersScreenState.UsersState -> {
                    adapter.updateDataSet(state.users.map { UserItem(it) })
                }
            }
        }
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return when (val item = adapter.getItem(position)) {
            is UserItem -> {
                navigator.navigateTo(UserScreen())
                true
            }
            else -> false
        }
    }
}

