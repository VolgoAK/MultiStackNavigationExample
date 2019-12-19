package com.example.singleactivityexample.screens.albums.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.Album
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_album.view.*


class ItemAlbum(val album: Album) : AbstractFlexibleItem<ItemAlbum.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.tvName.text = album.title
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = ItemAlbum.ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is ItemAlbum

    override fun getLayoutRes() = R.layout.item_album

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {
        val tvName = view.tvAlbumName
    }
}