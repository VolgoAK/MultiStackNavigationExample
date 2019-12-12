package com.example.singleactivityexample.screens.posts.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.Post
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_post.view.*

class PostItem(
    val post: Post
) : AbstractFlexibleItem<PostItem.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        with(holder) {
            tvTitle.text = post.title
            tvContent.text = post.body
        }
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) =
        ViewHolder(
            view,
            adapter
        )

    override fun equals(other: Any?) = other is PostItem

    override fun getLayoutRes() = R.layout.item_post

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {
        val tvTitle = view.tvPostTitle
        val tvDate = view.tvPostDate
        val tvContent = view.tvPostContent
        val tvCommentCount = view.tvPostCommentsCount
    }
}