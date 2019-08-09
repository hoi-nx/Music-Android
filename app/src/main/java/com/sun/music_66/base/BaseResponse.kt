package com.sun.music_66.base

import com.sun.music_66.constant.MessageKey

data class BaseResponse<T>(var result: T?, var error: Exception?,var message:String) {
    companion object {
        fun <T> success(data: T?): BaseResponse<T> {
            return BaseResponse(data, null, MessageKey.SUCCESS.message)
        }

        fun <T> error(error: Exception): BaseResponse<T> {
            return BaseResponse(null, error,MessageKey.FAILURE.message)
        }

    }
}
