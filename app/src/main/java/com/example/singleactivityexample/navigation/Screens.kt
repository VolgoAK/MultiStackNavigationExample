package com.example.singleactivityexample.navigation

import android.os.Parcelable
import com.example.singleactivityexample.model.User
import com.example.singleactivityexample.screens.album.AlbumFragment
import com.example.singleactivityexample.screens.albums.AlbumsFragment
import com.example.singleactivityexample.screens.newComment.NewCommentFragment
import com.example.singleactivityexample.screens.post.PostFragment
import com.example.singleactivityexample.screens.posts.PostsFragment
import com.example.singleactivityexample.screens.user.UserFragment
import com.example.singleactivityexample.screens.main.UsersMainFragment
import com.example.singleactivityexample.screens.users.UsersFragment
import kotlinx.android.parcel.Parcelize

sealed class MyAppScreen : CustomSupportScreen() {
    fun withFadeInOutAnimation(): MyAppScreen {
        withCustomAnimations(
            android.R.animator.fade_in, android.R.animator.fade_out,
            android.R.animator.fade_in, android.R.animator.fade_out
        )
        return this
    }
}

sealed class MyParcelableScreen: MyAppScreen(), Parcelable
@Parcelize
class PostsScreen(val scopeId: String) : MyParcelableScreen() {
    override fun createFragment() = PostsFragment.newInstance(scopeId)
}

class PostScreen(private val postId: Long,
                 private val scopeId: String) : MyAppScreen() {
    override fun createFragment() = PostFragment.newInstance(postId, scopeId)
}

class WriteCommentScreen() : MyAppScreen() {
    override fun createFragment() = NewCommentFragment()
}

@Parcelize
class UsersScreen(val scopeId: String): MyParcelableScreen() {
    override fun createFragment() = UsersFragment.newInstance(scopeId)
}

@Parcelize
class AlbumsScreen(val scopeId: String): MyParcelableScreen() {
    override fun createFragment() = AlbumsFragment.newInstance(scopeId)
}

class UsersMainScreen(): MyAppScreen() {
    override fun createFragment() = UsersMainFragment.newInstance()
}

@Parcelize
class UserScreen(val user: User, val sharedElementName: String?): MyParcelableScreen() {

    init {
        if(sharedElementName != null) withSharedElement(sharedElementName, sharedElementName)
    }

    override fun createFragment() = UserFragment.newInstance(user, sharedElementName)
}

@Parcelize
class AlbumScreen(): MyParcelableScreen() {
    override fun createFragment() = AlbumFragment.newInstance()
}