package com.example.singleactivityexample.navigation

import com.example.singleactivityexample.screens.newComment.NewCommentFragment
import com.example.singleactivityexample.screens.post.PostFragment
import com.example.singleactivityexample.screens.posts.PostsFragment

sealed class MyAppScreen : CustomSupportScreen() {
    fun withFadeInOutAnimation(): MyAppScreen {
        withCustomAnimations(
            android.R.animator.fade_in, android.R.animator.fade_out,
            android.R.animator.fade_in, android.R.animator.fade_out
        )
        return this
    }
}

class PostsScreen : MyAppScreen() {
    override fun createFragment() = PostsFragment()
}

class PostScreen(private val postId: Long) : MyAppScreen() {
    override fun createFragment() = PostFragment.newInstance(postId)
}

class WriteCommentScreen() : MyAppScreen() {
    override fun createFragment() = NewCommentFragment()
}
