package com.sun.music_66.view.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import com.sun.music_66.view.genres.GenreFragment
import com.sun.music_66.view.library.LibraryFragment
import com.sun.music_66.view.SettingFragment

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ViewpagerFragmentAdapter(fragmentManager: FragmentManager) : FragmentPagerAdapter(fragmentManager) {

    private val fragments = listOf<Fragment>(
            GenreFragment.newInstance(),
            LibraryFragment.newInstance(),
            SettingFragment.newInstance()
    )

    override fun getItem(position: Int): Fragment = fragments[position]

    override fun getCount(): Int = fragments.size

}
