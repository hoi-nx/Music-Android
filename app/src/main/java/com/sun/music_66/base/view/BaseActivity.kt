package com.sun.music_66.base.view

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.sun.music_66.R
import com.sun.music_66.base.IBaseViewMain
import com.sun.music_66.exceptions.NetworkException
import com.sun.music_66.exceptions.ServiceException
import com.sun.music_66.util.AlertDialogUtil.showMessageDialog

abstract class BaseActivity : AppCompatActivity(), IBaseViewMain {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getContentViewId())
        initializeData(savedInstanceState)
        initializeComponents()
        registerListeners()

    }

    override fun onDestroy() {
        unregisterListeners()
        super.onDestroy()
    }

    fun handleBusinessException(throwable: Throwable) {
        if (throwable is NetworkException) {
            showMessageDialog(this,getString(R.string.internet_disconnect), false) {}
            return
        }
        if(throwable is ServiceException){
            Toast.makeText(this,getString(R.string.server_error), Toast.LENGTH_LONG).show()
            return
        }
    }

    open fun registerListeners() {}

    open fun unregisterListeners() {}
}
