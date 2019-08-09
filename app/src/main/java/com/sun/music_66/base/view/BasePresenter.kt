package com.sun.music_66.base.view

import java.lang.ref.WeakReference

/**
 * Created by nguyenxuanhoi on 2019-07-15.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */

abstract class BasePresenter<V> {

    private var view: WeakReference<V>? = null

    fun setView(view: V) {
        this.view = WeakReference(view)
    }

    protected fun getView(): V? = view?.get()
}

