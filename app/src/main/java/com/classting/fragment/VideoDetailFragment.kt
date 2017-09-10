package com.classting.fragment

import android.content.res.Configuration
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.classting.R
import com.classting.log.Logger
import kotlinx.android.synthetic.main.fragment_video_detail.*
import kotlin.properties.Delegates

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class VideoDetailFragment : Fragment() {
    private lateinit var videoURL: String
    var position : Int by Delegates.notNull<Int>()
    private var positionMs: Long by Delegates.notNull()

    companion object {
        fun getInstance(position: Int, videoURL: String, positionMs: Long): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putInt("position", position)
            bundle.putString("videoURL", videoURL)
            bundle.putLong("positionMs", positionMs)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v("fragment on create")
        if (savedInstanceState == null) {
            position = arguments.getInt("position")
            videoURL = arguments.getString("videoURL")
            positionMs = arguments.getLong("positionMs")
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
        classtingVideoView.showController()
        classtingVideoView.setData(videoURL)
        classtingVideoView.continuePlay(positionMs)
        classtingVideoView.playVideo()
    }

    override fun onPause() {
        super.onPause()
        classtingVideoView.pauseVideo()
    }


}