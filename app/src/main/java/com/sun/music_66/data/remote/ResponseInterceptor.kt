package com.sun.music_66.data.remote

import com.sun.music_66.exceptions.ServiceException
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody

/**
 * Created by nguyenxuanhoi on 2019-07-17.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class ResponseInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val response = chain.proceed(request)
        return validateResponse(response)
    }

    private fun validateResponse(response: Response): Response {
        if (!response.isSuccessful) {
            throw ServiceException(response.message())
        }
        val responseBody = response.body() ?: return response
        val json = responseBody.string()
        return response.newBuilder()
                .body(ResponseBody.create(responseBody.contentType(), json))
                .build()
    }
}
