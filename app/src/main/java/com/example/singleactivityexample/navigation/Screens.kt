package com.example.singleactivityexample.navigation

import com.example.singleactivityexample.model.Post
import com.example.singleactivityexample.screens.newComment.NewCommentFragment
import com.example.singleactivityexample.screens.post.PostFragment
import com.example.singleactivityexample.screens.posts.PostsFragment
import ru.terrakok.cicerone.android.support.SupportAppScreen

class PostsScreen : SupportAppScreen() {
    override fun getFragment() = PostsFragment()
}

class PostScreen(private val post: Post) : SupportAppScreen() {
    override fun getFragment() = PostFragment.newInstance(post)
}

class WriteCommentScreen() : SupportAppScreen() {
    override fun getFragment() =
        NewCommentFragment()
}
