package com.sun.music_66.view.genres

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment
import com.sun.music_66.constant.ScreenAnimation
import com.sun.music_66.data.dto.GenreDto
import com.sun.music_66.data.dto.TrackDto
import com.sun.music_66.service.DownloadIntentService
import com.sun.music_66.util.ActivityExtension
import com.sun.music_66.view.tab.DownloadPresenter

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class GenreFragment : BaseFragment() {


    override fun getContentViewId(): Int = R.layout.fragment_genres_parent

    override fun initializeData(savedInstanceState: Bundle?) {
    }

    override fun initializeComponents() {
        ActivityExtension.addFragment(
            childFragmentManager, ChildGenreFragment.newInstance(),
            ScreenAnimation.OPEN_FULL, R.id.rootLayout, isAddFragment = true, isAddBackStack = true
        )
    }

    fun openListSongFragment(genre: GenreDto) {
        ActivityExtension.addFragment(
            childFragmentManager, ListSongFragment.newInstance(genre),
            ScreenAnimation.OPEN_FULL, R.id.rootLayout, isAddFragment = false, isAddBackStack = true
        )
    }
    fun openSearchFragment() {
        ActivityExtension.addFragment(
                childFragmentManager, SearchFragment.newInstance(),
                ScreenAnimation.OPEN_FULL, R.id.rootLayout, isAddFragment = false, isAddBackStack = true
        )
    }

    fun popStack():Boolean {
        val count = childFragmentManager.backStackEntryCount
        if (count > 1) {
            val fragment =childFragmentManager.findFragmentByTag(ListSongFragment::class.java.name)
            fragment?.let {
                childFragmentManager.popBackStackImmediate(
                        ListSongFragment::class.java.name,
                        FragmentManager.POP_BACK_STACK_INCLUSIVE)
                return true
            }
            childFragmentManager.popBackStackImmediate(
                    SearchFragment::class.java.name,
                    FragmentManager.POP_BACK_STACK_INCLUSIVE)
            return true
        }
        return false
    }



    companion object {
        @JvmStatic
        fun newInstance() = GenreFragment()
    }
}
