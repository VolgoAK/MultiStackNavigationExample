package com.example.singleactivityexample.screens.post

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.*
import com.example.singleactivityexample.navigation.WriteCommentScreen
import com.example.singleactivityexample.model.Post
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.screens.post.adapter.CommentItem
import com.example.singleactivityexample.screens.post.adapter.NewCommentItem
import com.example.singleactivityexample.screens.post.adapter.PostContentItem
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.IFlexible
import kotlinx.android.synthetic.main.fragment_post.*
import kotlinx.coroutines.flow.collect
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

        fun newInstance(post: Post) = PostFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_POST, post)
            }
        }
    }

    private val viewModel by viewModel<PostViewModel>{
        parametersOf(getExtraNotNull(EXTRA_POST))
    }
    private val navigator by inject<Navigator>()

    private val postAdapter = FlexibleAdapter(emptyList(), this)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(toolbar) {
            navigationIcon = requireContext().getDrawable(R.drawable.ic_arrow_back)
            setNavigationOnClickListener {
                requireActivity().onBackPressed()
            }
            title = viewModel.title
        }

        with(rvPost) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = postAdapter
        }

        viewModel.stateLiveData.observeSafe(viewLifecycleOwner, ::onNewState)
    }

    private fun onNewState(state: PostViewModel.PostScreenState) {
        when(state) {
            is PostViewModel.PostScreenState.LoadingState -> {
                progress.makeVisible()
                rvPost.makeGone()
            }
            is PostViewModel.PostScreenState.PostState -> {
                populatePost(state)
            }
            is PostViewModel.PostScreenState.ErrorState -> {
                requireContext().toast(state.error)
            }
        }
    }

    private fun populatePost(postState: PostViewModel.PostScreenState.PostState) {
        progress.makeGone()
        rvPost.makeVisible()

        val items = mutableListOf<IFlexible<*>>()
        items.add(PostContentItem(postState.post))
        items.addAll(postState.comments.map { CommentItem(it) })
        items.add(NewCommentItem())
        postAdapter.updateDataSet(items)
    }

    override fun onItemClick(view: View?, position: Int): Boolean {
        return when(val item = postAdapter.currentItems[position]) {
            is NewCommentItem -> {
                viewModel.onWriteNewCommentClicked()
                navigator.navigateTo(WriteCommentScreen())
                true
            }
            else -> false
        }
    }
}
