package com.example.singleactivityexample.screens.albums

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.observeSafe
import com.example.singleactivityexample.extensions.setVisibility
import com.example.singleactivityexample.extensions.toast
import com.example.singleactivityexample.screens.albums.adapter.ItemAlbum
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsFragment : Fragment(R.layout.fragment_albums) {

    companion object {
        fun newInstance() = AlbumsFragment()
    }

    private val viewModel by viewModel<AlbumsViewModel>()
    private val albumsAdapter = FlexibleAdapter(emptyList())

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(rvAlbums) {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = albumsAdapter
        }

        viewModel.stateLiveData.observeSafe(viewLifecycleOwner, ::onNewState)
    }

    private fun onNewState(state: AlbumsScreenState) {
        progress.setVisibility(state.loading)

        albumsAdapter.updateDataSet(
            state.albums.map { ItemAlbum(it) }
        )

        state.error?.let { requireContext().toast(it) }
    }
}
