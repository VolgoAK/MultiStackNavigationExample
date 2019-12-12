package com.example.singleactivityexample.screens.post.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityexample.R
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder


class NewCommentItem() : AbstractFlexibleItem<NewCommentItem.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {

    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = NewCommentItem.ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is NewCommentItem

    override fun getLayoutRes() = R.layout.item_write_comment

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {

    }
}