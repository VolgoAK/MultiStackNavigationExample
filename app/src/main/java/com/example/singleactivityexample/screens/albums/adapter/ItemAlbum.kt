package com.example.singleactivityexample.screens.albums.adapter


import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.singleactivityexample.R
import com.example.singleactivityexample.model.Album
import com.example.singleactivityexample.model.Photo
import com.example.singleactivityexample.model.User
import eu.davidea.flexibleadapter.FlexibleAdapter
import eu.davidea.flexibleadapter.items.AbstractFlexibleItem
import eu.davidea.flexibleadapter.items.IFlexible
import eu.davidea.viewholders.FlexibleViewHolder
import kotlinx.android.synthetic.main.item_album.view.*


class ItemAlbum(
    val album: Album,
    var photos: List<Photo>,
    var user: User?,
    val rvPool: RecyclerView.RecycledViewPool) :
    AbstractFlexibleItem<ItemAlbum.ViewHolder>() {

    override fun bindViewHolder(
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        holder: ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        holder.tvName.text = album.title
        val items = if(photos.isNotEmpty()) {
            photos.map { ItemPhotoInAlbum(it) }
        } else {
            (0..10).map { ItemPhotoInAlbum(null) }
        }
        holder.photosAdapter.updateDataSet(items)
    }

    override fun createViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?
    ) = ItemAlbum.ViewHolder(view, adapter, rvPool)

    override fun equals(other: Any?) = other is ItemAlbum

    override fun getLayoutRes() = R.layout.item_album

    class ViewHolder(
        view: View,
        adapter: FlexibleAdapter<IFlexible<RecyclerView.ViewHolder>>?,
        viewPool: RecyclerView.RecycledViewPool
    ) :
        FlexibleViewHolder(view, adapter) {
        val tvName = view.tvAlbumName
        val photosAdapter = FlexibleAdapter(emptyList())

        init {
            with(view.rvPhotos) {
                layoutManager =
                    LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
                setRecycledViewPool(viewPool)
                this.adapter = photosAdapter
            }
        }
    }
}