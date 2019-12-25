package com.example.singleactivityexample.screens.albums.adapter


import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.Photo
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_photo_in_album.view.*


class ItemPhotoInAlbum(var photo: Photo?) : AbstractFlexibleItem<ItemPhotoInAlbum.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        photo?.let {
            Glide.with(holder.itemView)
                .load(photo)
                .into(holder.ivPhoto)
        } ?: run {
            holder.ivPhoto.setImageResource(R.drawable.background_gray)
        }
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = ItemPhotoInAlbum.ViewHolder(view, adapter)

    override fun equals(other: Any?) = other is ItemPhotoInAlbum

    override fun getLayoutRes() = R.layout.item_photo_in_album

    class ViewHolder(view: View, adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?) :
        FlexibleViewHolder(view, adapter) {
        val ivPhoto = view.ivPhoto
    }
}