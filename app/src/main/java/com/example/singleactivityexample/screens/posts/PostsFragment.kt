package com.example.singleactivityexample.screens.posts


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.observeSafe
import com.example.singleactivityexample.extensions.toast
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.navigation.PostScreen
import com.example.singleactivityexample.screens.posts.adapter.PostItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass.
 */
class PostsFragment : Fragment(R.layout.fragment_posts), FlexibleAdapter.OnItemClickListener {

    private val viewModel by viewModel<PostsViewModel>()
    private val navigator by inject<Navigator>()
    private val adapter = FlexibleAdapter(emptyList(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar.title = "Posts"
        rvPosts.layoutManager = LinearLayoutManager(requireContext())
        rvPosts.adapter = adapter

        refresh.setOnRefreshListener { viewModel.refreshData() }

        viewModel.stateLiveData.observeSafe(viewLifecycleOwner, ::onNewState)
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return when (val item = adapter.currentItems[position]) {
            is PostItem -> {
                navigator.navigateTo(
                    PostScreen(item.post)
                )
                true
            }
            else -> false
        }
    }

    private fun onNewState(state: PostsViewModel.PostsScreenState) {
        when (state) {
            is PostsViewModel.PostsScreenState.PostsLoadedState -> {
                refresh.isRefreshing = false
                adapter.updateDataSet(state.posts.map { PostItem(it) })
            }
            is PostsViewModel.PostsScreenState.LoadingState -> {
                refresh.isRefreshing = true
            }
            is PostsViewModel.PostsScreenState.ErrorState -> {
                requireContext().toast(state.error)
            }
        }
    }
}
