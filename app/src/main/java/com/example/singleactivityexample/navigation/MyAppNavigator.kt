package com.example.singleactivityexample.navigation

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.singleactivityexample.extensions.getExtra
import ru.terrakok.cicerone.android.support.SupportAppNavigator
import ru.terrakok.cicerone.commands.Command

open class MyAppNavigator : SupportAppNavigator{

    constructor(activity: FragmentActivity, fragmentManager: FragmentManager, containerId: Int): super(activity, fragmentManager, containerId)
    constructor(activity: FragmentActivity, containerId: Int): super(activity, containerId)

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