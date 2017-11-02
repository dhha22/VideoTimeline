package com.dhha22.videotimeline.view.contract

import android.content.Intent

/**
 * Created by DavidHa on 2017. 9. 6..
 */
interface MainContract {
    interface View

    interface Presenter {
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
        fun resumeVideo()
        fun pauseVideo()
        fun destroyVideo()
    }
}