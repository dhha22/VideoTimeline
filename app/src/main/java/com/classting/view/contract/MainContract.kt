package com.classting.view.contract

import android.content.Intent
import android.support.v7.widget.RecyclerView

/**
 * Created by DavidHa on 2017. 9. 6..
 */
interface MainContract {
    interface View {

    }

    interface Presenter {
        fun resumeVideo()
        fun pauseVideo()
        fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent)
    }
}