package com.sun.music_66.view.adapter

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.music_66.R
import com.sun.music_66.view.SettingFragment
import com.sun.music_66.view.genres.GenreFragment
import com.sun.music_66.view.library.LibraryFragment
import com.sun.music_66.view.tab.DownloadFragment
import com.sun.music_66.view.tab.MySongFragment

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class LibraryAdapter (val context: Context,fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = listOf<Fragment>(
            MySongFragment.newInstance(),
            DownloadFragment.newInstance()
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence? {
        return when(position){
            0-> context.getString(R.string.title_my_song)
            1-> context.getString(R.string.title_download)
            else-> "Nothing"
        }
    }
}