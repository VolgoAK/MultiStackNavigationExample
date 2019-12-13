package com.example.singleactivityexample.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentTransaction
import com.example.singleactivityexample.extensions.getExtra
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

class MyAppNavigator(activity: FragmentActivity, containerId: Int) :
    SupportAppNavigator(activity, containerId) {

    override fun setupFragmentTransaction(
        command: Command?,
        currentFragment: Fragment?,
        nextFragment: Fragment?,
        fragmentTransaction: FragmentTransaction?
    ) {
        nextFragment?.getExtra<CustomAnimation>(CustomSupportScreen.ARG_ANIMATION)?.let { anim ->
            fragmentTransaction?.setCustomAnimations(
                anim.enter, anim.exit, anim.popEnter, anim.popExit
            )
        }
    }
}