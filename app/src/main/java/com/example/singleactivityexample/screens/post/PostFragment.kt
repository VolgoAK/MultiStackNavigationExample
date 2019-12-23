package com.example.singleactivityexample.screens.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.*
import com.example.singleactivityexample.model.Comment
import com.example.singleactivityexample.navigation.WriteCommentScreen
import com.example.singleactivityexample.model.Post
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.navigation.UsersMainScreen
import com.example.singleactivityexample.navigation.UsersScreen
import com.example.singleactivityexample.screens.post.adapter.CommentItem
import com.example.singleactivityexample.screens.post.adapter.NewCommentItem
import com.example.singleactivityexample.screens.post.adapter.PostContentItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.flow.collect
import org.koin.android.ext.android.getKoin
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf
import timber.log.Timber

/**
 * A simple [Fragment] subclass.
 */
class PostFragment : Fragment(R.layout.fragment_post), FlexibleAdapter.OnItemClickListener {

    companion object {
        private const val EXTRA_POST = "extra_post"
        private const val EXTRA_SCOPE_ID = "extra_scope_id"

        fun newInstance(postId: Long, scopeId: String) = PostFragment().apply {
            arguments = Bundle().apply {
                putLong(EXTRA_POST, postId)
                putString(EXTRA_SCOPE_ID, scopeId)
            }
        }
    }

    private val viewModel by viewModel<PostViewModel>{
        parametersOf(getExtraNotNull(EXTRA_POST))
    }
    private val scope by lazy { getKoin().getScope(getExtraNotNull(EXTRA_SCOPE_ID)) }
    private val navigator by lazy { scope.get<Navigator>() }

    private val postAdapter = FlexibleAdapter(emptyList(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            navigationIcon = requireContext().getDrawable(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
        }

        with(rvPost) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }

        viewModel.stateLiveData.observeSafe(viewLifecycleOwner, ::onNewState)
    }

    private fun onNewState(state: PostScreenState) {
        rvPost.setVisibility(!state.loading)
        progress.setVisibility(state.loading)

        populatePost(state.post, state.comments)

        state.error?.let { requireContext().toast(it) }
    }

    private fun populatePost(post: Post?, comments: List<Comment>) {
        toolbar.title = post?.title ?: ""

        val items = mutableListOf<IFlexible<*>>()
        if(post != null) {
            items.add(PostContentItem(post))
        }
        items.addAll(comments.map { CommentItem(it) })
        items.add(NewCommentItem())
        postAdapter.updateDataSet(items)
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return when(val item = postAdapter.currentItems[position]) {
            is NewCommentItem -> {
                viewModel.onWriteNewCommentClicked()
                navigator.navigateTo(
                    WriteCommentScreen()
                )
                true
            }
            is CommentItem -> {
                navigator.navigateTo(UsersMainScreen())
                true
            }
            else -> false
        }
    }
}
