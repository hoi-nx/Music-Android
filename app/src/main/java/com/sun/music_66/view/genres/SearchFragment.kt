package com.sun.music_66.view.genres

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.MainActivity
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.view.adapter.ListSongAdapter
import com.sun.music_66.view.tab.BottomSheetFragment
import com.sun.music_66.view.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_search.*

/**
 * Created by nguyenxuanhoi on 2019-07-30.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class SearchFragment : BaseFragment(), SearchContract.View, SearchView.OnQueryTextListener {
    override fun onQueryTextSubmit(query: String?): Boolean {
        query?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            presenter.searchMusic(it)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        newText?.let {
            Toast.makeText(context, it, Toast.LENGTH_LONG).show()
            presenter.searchMusic(it)
        }
        return true
    }

    override fun onFailed(throwable: Throwable) {
        handleBusinessException(throwable)

    }

    override fun onSuccess(data: List<TrackDto>) {
        val list = ListSongAdapter({
            (activity as MainActivity).getService()?.let { mediaService ->
                if (it.isStream!!) {
                    mediaService.addTracks(data)
                    mediaService.setCurrentTrack(it)
                    mediaService.playMusic(it, true)
                    (activity as MainActivity).updateMiniPlayMusic(it)
                } else {
                    Toast.makeText(context, getString(R.string.message_fail_stream), Toast.LENGTH_LONG).show()
                }

            }
        }, {
            BottomSheetFragment.newInstance(it).show(childFragmentManager, BottomSheetFragment::class.java.name)
        })
        attachAdapterToRecyclerView(rcSearch, list, data)
    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, genresAdapter: ListSongAdapter,
                                            listData: List<TrackDto>
    ) {
        Log.d("TEST2", listData.size.toString())
        genresAdapter.submitList(listData)
        recyclerView.isFocusable = true
        with(recyclerView) {
            addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen._8dp)))
            setHasFixedSize(true)
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.VERTICAL, false)
            adapter = genresAdapter

        }


    }

    private val presenter by lazy {
        SearchPresenter()
    }

    override fun getContentViewId(): Int = R.layout.fragment_search


    override fun initializeData(savedInstanceState: Bundle?) {

    }

    override fun initializeComponents() {
        presenter.setView(this)
        searchView.setOnQueryTextListener(this)

    }

    companion object {
        @JvmStatic
        fun newInstance() = SearchFragment()
    }
}