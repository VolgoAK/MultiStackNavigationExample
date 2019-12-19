package com.example.singleactivityexample.screens.usermain

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.example.singleactivityexample.R
import com.example.singleactivityexample.screens.albums.AlbumsFragment
import com.example.singleactivityexample.screens.users.UsersFragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.fragment_users_main.*

class UsersMainFragment : Fragment(R.layout.fragment_users_main), BottomNavigationView.OnNavigationItemSelectedListener {

    companion object {
        private const val TAG_ALBUMS = "tag_albums"
        private const val TAG_USERS = "tag_users"
        fun newInstance() = UsersMainFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        bottomNav.setOnNavigationItemSelectedListener(this)

        if(savedInstanceState == null) {
            bottomNav.selectedItemId = R.id.navigationItemUsers
            setCurrentFragment(TAG_USERS)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.navigationItemUsers -> {
                setCurrentFragment(TAG_USERS)
                true
            }
            R.id.navigationItemAlbums -> {
                setCurrentFragment(TAG_ALBUMS)
                true
            }
            else -> false
        }
    }

    private fun setCurrentFragment(tag: String) {
        val fragmentManager = childFragmentManager
        var currentFragment: Fragment? = null
        fragmentManager.fragments.forEach { fragment ->
            if(fragment.isVisible) currentFragment = fragment
        }

        var newFragment = fragmentManager.findFragmentByTag(tag)
        if(currentFragment != null && newFragment != null && currentFragment == newFragment) return

        val transaction = fragmentManager.beginTransaction()

        if(newFragment == null) {
            transaction.add(R.id.usersMainContainer, createFragment(tag), tag)
        }

        currentFragment?.let { transaction.hide(it) }
        newFragment?.let { transaction.show(it) }

        transaction.commitNow()
    }

    private fun createFragment(tag: String): Fragment {
        return when(tag) {
            TAG_ALBUMS -> AlbumsFragment.newInstance()
            TAG_USERS -> UsersFragment.newInstance()
            else -> throw IllegalArgumentException("Unsupported tag $tag")
        }
    }
}
