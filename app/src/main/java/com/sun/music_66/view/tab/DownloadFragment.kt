package com.sun.music_66.view.tab

import android.os.Bundle
import android.os.TokenWatcher
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.sun.music_66.MainActivity
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.repository.TrackRepository
import com.sun.music_66.view.adapter.ListSongAdapter
import com.sun.music_66.view.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_download.*

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class DownloadFragment : BaseFragment(), SwipeRefreshLayout.OnRefreshListener {
    override fun onRefresh() {
        refresh()
        swipeRefresh.isRefreshing = false
    }

     fun refresh() {
        if (::adapter.isInitialized) {
            adapter.clear()
            adapter.submitList(presenter.getMusicDownload())
            adapter.notifyDataSetChanged()
        }
    }

    private lateinit var adapter: ListSongAdapter

    private val presenter by lazy {
        DownloadPresenter()
    }

    override fun getContentViewId(): Int = R.layout.fragment_download

    override fun initializeData(savedInstanceState: Bundle?) {
    }

    override fun initializeComponents() {
        initAdapter()
    }

     fun initAdapter() {
        val listSong = presenter.getMusicDownload()
        adapter= ListSongAdapter({
            (activity as MainActivity).getService()?.let { mediaService ->
                mediaService.addTracks(listSong)
                mediaService.setCurrentTrack(it)
                mediaService.playMusic(it, false)
                (activity as MainActivity).updateMiniPlayMusic(it)
            }
        },{

        })
        attachAdapterToRecyclerView(rcMusicDownload, adapter, listSong)
    }

    override fun registerListeners() {
        super.registerListeners()
        swipeRefresh.setOnRefreshListener(this)
    }

    private fun attachAdapterToRecyclerView(
        recyclerView: RecyclerView,
        genresAdapter: ListSongAdapter,
        list: List<TrackDto>
    ) {
        genresAdapter.submitList(list)
        with(recyclerView) {
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen._8dp)))
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = genresAdapter
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(): DownloadFragment = DownloadFragment()
    }

}