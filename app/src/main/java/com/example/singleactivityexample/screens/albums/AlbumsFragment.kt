package com.example.singleactivityexample.screens.albums

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.singleactivityexample.R
import com.example.singleactivityexample.extensions.getExtraNotNull
import com.example.singleactivityexample.extensions.observeSafe
import com.example.singleactivityexample.extensions.setVisibility
import com.example.singleactivityexample.extensions.toast
import com.example.singleactivityexample.navigation.AlbumScreen
import com.example.singleactivityexample.navigation.Navigator
import com.example.singleactivityexample.screens.albums.adapter.ItemAlbum
import eu.davidea.flexibleadapter.FlexibleAdapter
import kotlinx.android.synthetic.main.fragment_albums.*
import org.koin.android.ext.android.getKoin
import org.koin.androidx.viewmodel.ext.android.viewModel

class AlbumsFragment : Fragment(R.layout.fragment_albums), FlexibleAdapter.OnItemClickListener {

    companion object {
        private const val EXTRA_SCOPE_ID = "extra_scope_id"
        fun newInstance(scopeId: String) = AlbumsFragment().apply {
            arguments = Bundle().apply {
                putString(EXTRA_SCOPE_ID, scopeId)
            }
        }
    }

    private val viewModel by viewModel<AlbumsViewModel>()
    private val albumsAdapter = FlexibleAdapter(emptyList(), this)

    private val scope by lazy { getKoin().getScope(getExtraNotNull(EXTRA_SCOPE_ID)) }
    private val navigator by lazy { scope.get<Navigator>() }

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

    override fun onItemClick(view: View?, position: Int): Boolean {
        return when(val item = albumsAdapter.getItem(position)) {
            is ItemAlbum -> {
                navigator.navigateTo(AlbumScreen())
                true
            }
            else -> false
        }
    }
}
