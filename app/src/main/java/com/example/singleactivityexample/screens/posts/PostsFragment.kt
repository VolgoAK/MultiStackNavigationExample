package com.example.singleactivityexample.screens.posts


import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.extraNotNull
import com.example.singleactivityexample.extensions.observeSafe
import com.example.singleactivityexample.extensions.toast
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.navigation.PostScreen
import com.example.singleactivityexample.screens.posts.adapter.PostItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_posts.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PostsFragment : Fragment(R.layout.fragment_posts), FlexibleAdapter.OnItemClickListener {

    companion object {
        private const val EXTRA_SCOPE_ID = "extra_scope_id"
        fun newInstance(scopeId: String) = PostsFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_SCOPE_ID, scopeId)
            }
        }
    }

    private val scopeId by extraNotNull<String>(EXTRA_SCOPE_ID)
    private val scope by lazy { getKoin().getScope(scopeId) }
    private val navigator by lazy { scope.get<Navigator>() }
    private val viewModel by viewModel<PostsViewModel>()
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
                    PostScreen(item.post.id, scopeId)
                        .withFadeInOutAnimation()
                )
                true
            }
            else -> false
        }
    }

    private fun onNewState(state: PostsScreenState) {
        refresh.isRefreshing = state.loading
        adapter.updateDataSet(state.posts.map { PostItem(it) })
        state.errorMessage?.let {
            requireContext().toast(it)
        }
    }
}
