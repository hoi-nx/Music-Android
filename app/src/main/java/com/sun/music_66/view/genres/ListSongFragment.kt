package com.sun.music_66.view.genres

import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.sun.music_66.MainActivity
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.util.LoadImage
import com.sun.music_66.view.tab.BottomSheetFragment
import com.sun.music_66.view.adapter.ListSongAdapter
import com.sun.music_66.view.widget.SpaceItemDecoration
import kotlinx.android.synthetic.main.content_scrolling.*
import kotlinx.android.synthetic.main.layout_header_listsong.*

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ListSongFragment : BaseFragment(), ListSongContract.View {
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
        attachAdapterToRecyclerView(rc_list_song, list, data)

    }

    private val presenter by lazy {
        ListSongPresenter()
    }

    override fun getContentViewId(): Int = R.layout.fragment_list_song

    override fun initializeData(savedInstanceState: Bundle?) {
    }

    override fun initializeComponents() {
        presenter.setView(this)
        val genreDto = arguments?.getParcelable(Constants.ARGUMENT_GENRES) as GenreDto
        text_genres.text = genreDto.nameGenre
        LoadImage.loadImage(image_genres, genreDto.image)
        presenter.getMusicByGenres(genreDto.id)
    }

    private fun attachAdapterToRecyclerView(
            recyclerView: RecyclerView?,
            genresAdapter: ListSongAdapter,
            listData: List<TrackDto>
    ) {
        genresAdapter.submitList(listData)
        recyclerView?.let {
            with(it) {
                addItemDecoration(SpaceItemDecoration(resources.getDimensionPixelSize(R.dimen._8dp)))
                setHasFixedSize(true)
                layoutManager = LinearLayoutManager(requireContext())
                adapter = genresAdapter
            }
        }

    }

    companion object {
        @JvmStatic
        fun newInstance(genres: GenreDto): ListSongFragment {
            val fragment = ListSongFragment()
            val args = Bundle()
            args.putParcelable(Constants.ARGUMENT_GENRES, genres)
            fragment.arguments = args
            return fragment
        }
    }

}