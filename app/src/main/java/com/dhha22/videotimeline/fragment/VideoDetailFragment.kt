package com.dhha22.videotimeline.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dhha22.videotimeline.R
import com.dhha22.videotimeline.log.Logger
import kotlinx.android.synthetic.main.fragment_video_detail.*
import kotlin.properties.Delegates

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class VideoDetailFragment : Fragment() {
    private var feedId by Delegates.notNull<Long>()
    private lateinit var videoURL: String
    var position by Delegates.notNull<Int>()
    private var positionMs: Long by Delegates.notNull()
    private var isRecorded: Boolean by Delegates.notNull()

    companion object {
        fun getInstance(feedId: Long, position: Int, videoURL: String, positionMs: Long, isRecorded: Boolean): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putLong("feedId", feedId)
            bundle.putInt("position", position)
            bundle.putString("videoURL", videoURL)
            bundle.putLong("positionMs", positionMs)
            bundle.putBoolean("isRecorded", isRecorded)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v("fragment on create")
        if (savedInstanceState == null) {
            feedId = arguments.getLong("feedId")
            position = arguments.getInt("position")
            videoURL = arguments.getString("videoURL")
            positionMs = arguments.getLong("positionMs")
            isRecorded = arguments.getBoolean("isRecorded")
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        Logger.v("fragment on create view")
        val view = inflater?.inflate(R.layout.fragment_video_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Logger.v("fragment on view crated")
        classtingVideoView.showController() // 비디오 플레이어 컨트롤러
        classtingVideoView.setData(feedId, videoURL)
        classtingVideoView.continuePlay(positionMs)
        classtingVideoView.playVideo()
        classtingVideoView.isRecorded = isRecorded
    }
}