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

        // 비디오 상세화면으로 이동
        fun goVideoDetail(context: Context, feedId: Long, position: Int, videoURL: String, positionMs: Long = 0, isRecorded:Boolean) {
            val intent = Intent(context, NavigationActivity::class.java)
            NavigationActivity.fragment = VideoDetailFragment.getInstance(feedId, position, videoURL, positionMs, isRecorded)
            (context as Activity).startActivityForResult(intent, VIDEO_DETAIL)
        }
    }
}