package com.sun.music_66.view

import android.os.Bundle
import com.sun.music_66.R
import com.sun.music_66.base.view.BaseFragment

/**
 * Created by nguyenxuanhoi on 2019-07-19.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class SettingFragment : BaseFragment() {

    override fun getContentViewId(): Int = R.layout.fragment_setting

    override fun initializeData(savedInstanceState: Bundle?) {

    }

    override fun initializeComponents() {
    }

    companion object {
        @JvmStatic
        fun newInstance() = SettingFragment()
    }

}
