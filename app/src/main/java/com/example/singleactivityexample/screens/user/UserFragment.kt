package com.example.singleactivityexample.screens.user

import android.os.Bundle
import android.view.View
import android.view.ViewTreeObserver
import androidx.fragment.app.Fragment
import androidx.transition.TransitionInflater
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.extra
import com.example.singleactivityexample.extensions.extraNotNull
import com.example.singleactivityexample.model.User
import com.example.singleactivityexample.utils.getImage
import kotlinx.android.synthetic.main.fragment_user.*

class UserFragment : Fragment(R.layout.fragment_user) {
    companion object {
        private const val ARG_SHARED_ELEMENT_NAME = "shared_element_name"
        private const val ARG_USER = "arg_user"
        fun newInstance(user: User, sharedElementName: String?) = UserFragment().apply {
            arguments = Bundle().apply {
                putString(ARG_SHARED_ELEMENT_NAME, sharedElementName)
                putParcelable(ARG_USER, user)
            }
        }
    }

    private val sharedElementName by extra<String>(ARG_SHARED_ELEMENT_NAME)
    private val extraUser by extraNotNull<User>(ARG_USER)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        postponeEnterTransition()
        sharedElementEnterTransition = TransitionInflater.from(context)
            .inflateTransition(android.R.transition.move);
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedElementName?.let { ivUserAvatarBig.transitionName = it }

        view.viewTreeObserver.addOnGlobalLayoutListener(object :
            ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                view.viewTreeObserver.removeOnGlobalLayoutListener(this)
                startPostponedEnterTransition()

                Glide.with(view)
                    .load(extraUser.getImage())
                    .apply(RequestOptions().centerCrop())
                    .into(ivUserAvatarBig)
            }
        })
    }
}
