package com.sun.music_66.extentions

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes

fun ViewGroup.inflate(@LayoutRes layoutResource: Int) = LayoutInflater.from(context).inflate(layoutResource, this, false)!!
