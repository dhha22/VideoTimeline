package com.classting.fragment

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
    private var positionMs: Long by Delegates.notNull()

    companion object {
        fun getInstance(videoURL: String, positionMs: Long): VideoDetailFragment {
            val fragment = VideoDetailFragment()
            val bundle = Bundle()
            bundle.putString("videoURL", videoURL)
            bundle.putLong("positionMs", positionMs)
            fragment.arguments = bundle
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        videoURL = arguments.getString("videoURL")
        positionMs = arguments.getLong("positionMs")
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater?.inflate(R.layout.fragment_video_detail, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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