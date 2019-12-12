package com.example.singleactivityexample.screens.post.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.Post
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_post_content.view.*


class PostContentItem(
    val post: Post
) : AbstractFlexibleItem<PostContentItem.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        with(holder) {
            tvContent.text = post.body
            tvAuthor.text = "no name"
            tvDate.text = "12.02.1984"
        }
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = PostContentItem.ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is PostContentItem

    override fun getLayoutRes() = R.layout.item_post_content

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {
        val tvContent = view.tvPostContent
        val tvAuthor = view.tvAuthor
        val tvDate = view.tvDate
    }
}