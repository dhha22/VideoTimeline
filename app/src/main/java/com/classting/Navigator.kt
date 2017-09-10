package com.classting

import android.app.Activity
import android.content.Context
import android.content.Intent
import com.classting.fragment.VideoDetailFragment

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class Navigator {
    companion object {
        val VIDEO_DETAIL = 100

        fun goVideoDetail(context: Context, position: Int,videoURL: String, positionMs: Long = 0) {
            val intent = Intent(context, NavigationActivity::class.java)
            NavigationActivity.fragment = VideoDetailFragment.getInstance(position, videoURL, positionMs)
            (context as Activity).startActivityForResult(intent, VIDEO_DETAIL)
        }
    }
}