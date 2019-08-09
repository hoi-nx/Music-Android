package com.sun.music_66.base

import android.os.AsyncTask
/**
 * Created by nguyenxuanhoi on 2019-07-18.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
abstract class BaseAsyncTask<Params, Progress, Result> : AsyncTask<Params, Progress, BaseResponse<Result>>() {

    override fun doInBackground(vararg params: Params): BaseResponse<Result> {
        return try {
            BaseResponse.success(onBackground(*params))
        } catch (e: Exception) {
            BaseResponse.error(e)
        }
    }

    override fun onPostExecute(result: BaseResponse<Result>) {
        result.error?.let {
            onFailure(it)
        }
        result.result?.let {
            onResult(it)
        }
        super.onPostExecute(result)
    }

    protected abstract fun onBackground(vararg params: Params): Result

    protected abstract fun onResult(result: Result)

    protected abstract fun onFailure(result: Throwable)
}
