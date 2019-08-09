package com.sun.music_66.base

interface OnDataLoadedCallback<T> {
    fun onSuccess(data: T)
    fun onFailed(throwable: Throwable)
}
