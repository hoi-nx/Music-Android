package com.sun.music_66.base.view

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.sun.music_66.base.IBaseViewMain
import com.sun.music_66.extentions.inflate

abstract class BaseFragment : Fragment(), IBaseViewMain {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return container?.inflate(getContentViewId())
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initializeData(savedInstanceState)
        initializeComponents()
        registerListeners()
    }

    override fun onDestroyView() {
        unregisterListeners()
        super.onDestroyView()
    }

    /** Handle Exception to show message */
    fun handleBusinessException(throwable: Throwable) {
        (activity as BaseActivity).handleBusinessException(throwable)
    }

    /** true if Back button was handled. */
    open fun onBackPressed(): Boolean = false

    open fun registerListeners() {}

    open fun unregisterListeners() {}

    open fun showLoading() {}

    open fun hideLoading() {}
}
