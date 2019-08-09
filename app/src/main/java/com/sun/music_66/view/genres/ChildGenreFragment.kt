package com.sun.music_66.view.genres

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.MainActivity
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.view.adapter.GenresAdapter
import com.sun.music_66.view.adapter.SilderAdapter
import kotlinx.android.synthetic.main.fragment_genre.*
import kotlinx.android.synthetic.main.layout_genres.*
import kotlinx.android.synthetic.main.layout_slide_trending.*
import java.util.*

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ChildGenreFragment : BaseFragment(), GenreContract.View, View.OnClickListener {
    private lateinit var adapterSlider: SilderAdapter

    private lateinit var timer: Timer

    private val presenter by lazy {
        GenrePresenter()
    }

    override fun getContentViewId(): Int = R.layout.fragment_genre

    override fun initializeData(savedInstanceState: Bundle?) {
        attachAdapterToRecyclerView(recycler_view, GenresAdapter {
            (parentFragment as GenreFragment).openListSongFragment(it)
        })
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.imageSearch -> {
                (parentFragment as GenreFragment).openSearchFragment()
            }
        }
    }

    private fun attachAdapterToRecyclerView(recyclerView: RecyclerView, genresAdapter: GenresAdapter) {
        genresAdapter.submitList(presenter.getListGenres())
        text_number_of_genres.text = presenter.getListGenres().size.toString()
        with(recyclerView) {
            layoutManager = LinearLayoutManager(requireContext(), RecyclerView.HORIZONTAL, false)
            adapter = genresAdapter
        }

    }

    private fun initViewPager(tracks: List<TrackDto>) {
        adapterSlider.submitList(tracks)

        timer.schedule(object : TimerTask() {
            override fun run() {
                activity?.runOnUiThread {
                    viewPagerSlide?.let {
                        var index = it.currentItem + 1 % adapterSlider.count
                        if (index == adapterSlider.count) {
                            index = 0
                        }
                        viewPagerSlide.setCurrentItem(index, true)
                    }
                }
            }
        }, 1000, 5000)

    }

    override fun initializeComponents() {
        presenter.setView(this)
        presenter.getMusic(Constants.GENRES_COUNTRY)
        adapterSlider = SilderAdapter()
        viewPagerSlide?.let {
            it.adapter = adapterSlider
        }
        timer = Timer()
    }

    override fun registerListeners() {
        imageSearch.setOnClickListener(this)
        adapterSlider.setOnItemClicked {
            (activity as MainActivity).getService()?.let { mediaService ->
                if (it.isStream!!) {
                    mediaService.addTracks(adapterSlider.getDatas())
                    mediaService.setCurrentTrack(it)
                    mediaService.playMusic(it, true)
                    (activity as MainActivity).updateMiniPlayMusic(it)
                } else {
                    Toast.makeText(context, getString(R.string.message_fail_stream), Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    override fun unregisterListeners() {
        if (::timer.isInitialized) timer.cancel()
    }

    override fun onSuccess(data: List<TrackDto>) {
        initViewPager(data)

    }

    override fun onFailed(throwable: Throwable) {
        handleBusinessException(throwable)

    }

    companion object {
        @JvmStatic
        fun newInstance() = ChildGenreFragment()
    }
}