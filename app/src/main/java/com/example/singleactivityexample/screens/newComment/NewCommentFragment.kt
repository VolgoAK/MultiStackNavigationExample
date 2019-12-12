package com.example.singleactivityexample.screens.newComment

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.singleactivityexample.navigation.PostsScreen
import com.example.singleactivityexample.R
import com.example.singleactivityexample.navigation.Navigator
import kotlinx.android.synthetic.main.fragment_new_comment.*
import org.koin.android.ext.android.inject


class NewCommentFragment : Fragment(R.layout.fragment_new_comment) {

    private val navigator by inject<Navigator>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        btDone.setOnClickListener {
            navigator.backTo(PostsScreen())
        }
    }
}
