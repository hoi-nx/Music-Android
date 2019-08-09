package com.sun.music_66.view.genres

import com.sun.music_66.base.view.BasePresenter
import com.sun.music_66.constant.Constants
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.repository.SearchRepository
import com.sun.music_66.repository.TrackRepository
import com.sun.music_66.util.StringUtils

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class SearchPresenter : BasePresenter<SearchContract.View>(), SearchContract.Presenter {
    private val responsitory by lazy {
        SearchRepository()
    }
    override fun searchMusic(keySearch: String) {
        responsitory.searchMusic(StringUtils.generateSearchUrl(keySearch,20,20))
        responsitory.setOnSuccess {
            getView()?.onSuccess(it)
        }
        responsitory.setOnFail {
            getView()?.onFailed(it)
        }

    }

}
