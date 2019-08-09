package com.sun.music_66.base

import android.os.Bundle
import androidx.annotation.LayoutRes

interface IBaseViewMain {

    @LayoutRes
    fun getContentViewId(): Int

    fun initializeData(savedInstanceState: Bundle?)

    fun initializeComponents()
}
