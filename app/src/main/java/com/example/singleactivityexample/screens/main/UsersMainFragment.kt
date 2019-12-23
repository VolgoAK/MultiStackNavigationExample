package com.example.singleactivityexample.screens.main

import android.os.Bundle
import android.view.MenuItem
import android.view.View
import androidx.core.view.iterator
import androidx.fragment.app.Fragment
import com.example.singleactivityexample.R
import com.example.singleactivityexample.base.BackButtonListener
import com.example.singleactivityexample.base.nestendcontainer.ContainerFragment
import com.example.singleactivityexample.di.containerModule
import com.example.singleactivityexample.navigation.AlbumsScreen
import com.example.singleactivityexample.navigation.PostsScreen
import com.example.singleactivityexample.navigation.UsersScreen
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_users_main.*
import org.koin.core.context.loadKoinModules

class UsersMainFragment : Fragment(R.layout.fragment_users_main),
    BottomNavigationView.OnNavigationItemSelectedListener,
    BackButtonListener {

    companion object {
        private const val TAG_ALBUMS = "tag_albums"
        private const val TAG_USERS = "tag_users"
        private const val TAG_NEWS = "tag_news"
        fun newInstance() =
            UsersMainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav.setOnNavigationItemSelectedListener(this)

        if (savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.navigationItemPosts
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.navigationItemUsers -> {
                setCurrentFragment(TAG_USERS)
                true
            }
            R.id.navigationItemAlbums -> {
                setCurrentFragment(TAG_ALBUMS)
                true
            }
            R.id.navigationItemPosts -> {
                setCurrentFragment(TAG_NEWS)
                true
            }
            else -> false
        }
    }

    private fun setCurrentFragment(tag: String) {
        val fragmentManager = childFragmentManager
        var currentFragment: Fragment? = null
        fragmentManager.fragments.forEach { fragment ->
            if (fragment.isVisible) currentFragment = fragment
        }

        var newFragment = fragmentManager.findFragmentByTag(tag)
        if (currentFragment != null && newFragment != null && currentFragment == newFragment) return

        val transaction = fragmentManager.beginTransaction()

        if (newFragment == null) {
            transaction.add(R.id.usersMainContainer, createFragment(tag), tag)
        }

        currentFragment?.let { transaction.hide(it) }
        newFragment?.let { transaction.show(it) }

        transaction.commitNow()
    }

    override fun onBackPressed(): Boolean {
        return getVisibleContainerFragment()?.onBackPressed() ?: false || popMainStack()
    }

    private fun getVisibleContainerFragment(): ContainerFragment? = childFragmentManager.fragments
        .find { it is ContainerFragment && it.isVisible } as? ContainerFragment

    private fun popMainStack(): Boolean {
        val fragmentManager = childFragmentManager
        var currentFragment: Fragment? = null
        var topFragmentInStack: Fragment? = null
        fragmentManager.fragments.forEach { fragment ->
            if (isTopFragment(fragment)) {
                if (fragment.isVisible) {
                    currentFragment = fragment
                } else {
                    topFragmentInStack = fragment
                }
            }
        }

        if (topFragmentInStack == null || currentFragment == null) return false
        val transition = fragmentManager.beginTransaction()
        transition.remove(currentFragment!!)
        transition.show(topFragmentInStack!!)

        transition.commitNow()

        changeBottomSelection(topFragmentInStack as ContainerFragment)
        return true
    }

    private fun changeBottomSelection(fragment: ContainerFragment) {
        val targetItemID = when (fragment.pageTag) {
            TAG_NEWS -> R.id.navigationItemPosts
            TAG_ALBUMS -> R.id.navigationItemAlbums
            TAG_USERS -> R.id.navigationItemUsers
            else -> return
        }

        bottomNav.menu.iterator()
            .forEach {
                val checked = it.itemId == targetItemID
                // Change selection only for item which should be selected now
                // it.isChecked = checked - won't work
                if(checked) it.isChecked = true
            }
    }

    private fun isTopFragment(fragment: Fragment): Boolean {
        return fragment is ContainerFragment
    }

    private fun createFragment(tag: String): Fragment {
        val scopeName = "Scope:$tag"
        return when (tag) {
            TAG_ALBUMS -> ContainerFragment.newInstance(scopeName, tag, AlbumsScreen(scopeName))
            TAG_USERS -> ContainerFragment.newInstance(scopeName, tag, UsersScreen(scopeName))
            TAG_NEWS -> ContainerFragment.newInstance(scopeName, tag, PostsScreen(scopeName))
            else -> throw IllegalArgumentException("Unsupported tag $tag")
        }
    }
}
