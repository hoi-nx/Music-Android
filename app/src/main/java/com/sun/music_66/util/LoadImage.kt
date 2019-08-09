package com.sun.music_66.util

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.sun.music_66.R

/**
 * Created by nguyenxuanhoi on 2019-07-24.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
object LoadImage {
    fun loadImage(image:ImageView,link:String?){
        link?.let {
            Glide.with(image.context).load(StringUtils.reformatImageUrl(link)).error(R.drawable.bg_default_image).into(image)
        }
    }
}
