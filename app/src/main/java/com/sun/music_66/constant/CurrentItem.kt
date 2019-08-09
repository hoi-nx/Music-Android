package com.sun.music_66.constant

import androidx.annotation.IntDef
/**
 * Created by nguyenxuanhoi on 2019-07-18.
 * @author nguyen.xuan.hoi@sun-asterisk.com
 */
@IntDef(CurrentItem.ZERO, CurrentItem.ONE, CurrentItem.TWO)
annotation class CurrentItem {
    companion object {
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
    }
}
