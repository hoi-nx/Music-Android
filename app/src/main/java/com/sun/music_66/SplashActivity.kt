package com.sun.music_66

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import com.sun.music_66.base.view.BaseActivity
import com.sun.music_66.constant.Constants

/**
 * Created by nguyenxuanhoi on 2019-07-22.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
class SplashActivity :BaseActivity(){
    override fun getContentViewId(): Int = R.layout.activity_splash_screen

    override fun initializeData(savedInstanceState: Bundle?) {

    }

    override fun initializeComponents() {
        Handler().postDelayed({
            val intent=Intent(this,MainActivity::class.java)
            startActivity(intent)
            finish()

        }, Constants.TIME_DELAY)

    }

}