package com.sun.music_66.view.genres

import com.sun.music_66.base.view.BasePresenter
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.repository.TrackRepository
import com.sun.music_66.util.StringUtils

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class GenrePresenter : BasePresenter<GenreContract.View>(), GenreContract.Presenter {
    private val repository by lazy {
        TrackRepository()
    }
    override fun getMusic(genreID: String) {
        repository.getListTrackTrending(StringUtils.generateTredingUrl(Constants.KIND_TREND, genreID))
        repository.setOnSuccess {
            getView()?.onSuccess(it)
        }
        repository.setOnFail {
            getView()?.onFailed(it)
        }
    }

    override fun getListGenres(): ArrayList<GenreDto> = repository.getListGenres()
}
