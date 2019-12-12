package com.example.singleactivityexample.screens.post.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.Comment
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_comment.view.*
import kotlinx.android.synthetic.main.item_post_content.view.*


class CommentItem(
    val comment: Comment
) : AbstractFlexibleItem<CommentItem.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        with(holder) {
            tvContent.text = comment.body
            tvAuthor.text = comment.name
        }
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = CommentItem.ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is CommentItem

    override fun getLayoutRes() = R.layout.item_comment

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {
        val civAvatar = view.civAvatar
        val tvAuthor = view.tvCommentAuthor
        val tvData = view.tvCommentDate
        val tvContent = view.tvCommentContent
    }
}