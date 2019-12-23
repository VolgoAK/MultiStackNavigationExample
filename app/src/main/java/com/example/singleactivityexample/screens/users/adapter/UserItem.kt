package com.example.singleactivityexample.screens.users.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.User
import com.example.singleactivityexample.utils.getImage
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_user.view.*


class UserItem(val user: User) : AbstractFlexibleItem<UserItem.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        with(holder) {
            tvName.text = user.name
            tvNickname.text = user.username
            Glide.with(itemView)
                .load(user.getImage())
                .into(ivAvatar)
        }
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = UserItem.ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is UserItem

    override fun getLayoutRes() = R.layout.item_user

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {
        val tvName = view.tvName
        val tvNickname = view.tvNickname
        val ivAvatar = view.ivUserAvatar
    }
}