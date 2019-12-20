package com.example.singleactivityexample.navigation

import android.os.Parcelable
import androidx.fragment.app.Fragment
import com.example.singleactivityexample.screens.album.AlbumFragment
import com.example.singleactivityexample.screens.albums.AlbumsFragment
import com.example.singleactivityexample.screens.newComment.NewCommentFragment
import com.example.singleactivityexample.screens.post.PostFragment
import com.example.singleactivityexample.screens.posts.PostsFragment
import com.example.singleactivityexample.screens.user.UserFragment
import com.example.singleactivityexample.screens.usermain.UsersMainFragment
import com.example.singleactivityexample.screens.users.UsersFragment
import kotlinx.android.parcel.Parcelize
import org.koin.core.scope.ScopeID
import timber.log.Timber

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

class PostsScreen : MyAppScreen() {
    override fun createFragment() = PostsFragment()
}

class PostScreen(private val postId: Long) : MyAppScreen() {
    override fun createFragment() = PostFragment.newInstance(postId)
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
class UserScreen(): MyParcelableScreen() {
    override fun createFragment() = UserFragment()
}

@Parcelize
class AlbumScreen(): MyParcelableScreen() {
    override fun createFragment() = AlbumFragment.newInstance()
}