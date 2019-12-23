package com.example.singleactivityexample.base.nestendcontainer

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import com.example.singleactivityexample.R
import com.example.singleactivityexample.base.BackButtonListener
import com.example.singleactivityexample.extensions.extraNotNull
import com.example.singleactivityexample.navigation.MyAppNavigator
import com.example.singleactivityexample.navigation.MyParcelableScreen
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.navigation.UsersScreen
import org.koin.android.ext.android.getKoin
import org.koin.core.context.loadKoinModules
import org.koin.core.qualifier.named


class ContainerFragment : Fragment(R.layout.fragment_container), BackButtonListener {

    companion object {
        private const val EXTRA_SCOPE_NAME = "extra_scope_id"
        private const val EXTRA_ROOT_SCREEN = "extra_root_screen"
        private const val EXTRA_PAGE_TAG = "extra_page_tag"

        fun newInstance(scopeId: String, pageTag: String, rootScreen: MyParcelableScreen) = ContainerFragment().apply {
            arguments = Bundle().apply {
                putParcelable(EXTRA_ROOT_SCREEN, rootScreen)
                putString(EXTRA_SCOPE_NAME, scopeId)
                putString(EXTRA_PAGE_TAG, pageTag)
            }
        }
    }

    val pageTag by extraNotNull<String>(EXTRA_PAGE_TAG)
    private val scopeId by extraNotNull<String>(EXTRA_SCOPE_NAME)
    private val rootScreen by extraNotNull<MyParcelableScreen>(EXTRA_ROOT_SCREEN)
    private val scope by lazy {
        getKoin().getOrCreateScope(scopeId, named(SCOPE_PAGE_ONE))
    }
    private lateinit var navigator: Navigator

    private val navigatorHolder by lazy{
        MyAppNavigator(
            requireActivity(),
            childFragmentManager,
            R.id.fragmentContainer
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(savedInstanceState == null) {
            loadKoinModules(containerModule)
        }
        navigator = scope.get()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState ==  null) {
            navigator.navigateTo(rootScreen)
        }
    }

    override fun onResume() {
        super.onResume()
        navigator.setNavigator(navigatorHolder)
    }

    override fun onPause() {
        super.onPause()
        navigator.removeNavigator()
    }

    override fun onBackPressed(): Boolean {
        if(childFragmentManager.backStackEntryCount > 1) {
            childFragmentManager.popBackStack()
            return true
        } else {
            return false
        }
        /*return (childFragmentManager.findFragmentById(R.id.fragmentContainer) as? BackButtonListener)
            ?.onBackPressed() ?: false*/
    }
}
