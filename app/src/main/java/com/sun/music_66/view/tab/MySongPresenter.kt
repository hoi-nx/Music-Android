package com.sun.music_66.view.tab

import com.sun.music_66.base.view.BasePresenter
import com.sun.music_66.data.local.MusicDevice

/**
 * Created by nguyenxuanhoi on 2019-07-29.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class MySongPresenter(val manager: MusicExternalManager) :BasePresenter<MySongContract.View>(), MySongContract.Presenter{
    override fun getAllMusic(): List<MusicDevice> = manager.getExternals()


}