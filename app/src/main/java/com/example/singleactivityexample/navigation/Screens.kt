package com.example.singleactivityexample.navigation

import androidx.fragment.app.Fragment
import com.example.singleactivityexample.screens.albums.AlbumsFragment
import com.example.singleactivityexample.screens.newComment.NewCommentFragment
import com.example.singleactivityexample.screens.post.PostFragment
import com.example.singleactivityexample.screens.posts.PostsFragment
import com.example.singleactivityexample.screens.usermain.UsersMainFragment
import com.example.singleactivityexample.screens.users.UsersFragment

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

class UsersScreen(): MyAppScreen() {
    override fun createFragment() = UsersFragment()
}

class AlbumsScreen(): MyAppScreen() {
    override fun createFragment() = AlbumsFragment()
}

class UsersMainScreen(): MyAppScreen() {
    override fun createFragment() = UsersMainFragment.newInstance()
}