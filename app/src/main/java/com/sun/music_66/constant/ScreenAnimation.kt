package com.sun.music_66.constant

import com.sun.music_66.R

enum class ScreenAnimation(val enterToRight: Int,
                           val exitToRight: Int,
                           val enterToLeft: Int,
                           val exitToLeft: Int) {
    OPEN_FULL(R.anim.slide_in_right, R.anim.slide_out_left, R.anim.slide_out_left, R.anim.slide_in_right),

    NONE(0, 0, 0, 0)
}
