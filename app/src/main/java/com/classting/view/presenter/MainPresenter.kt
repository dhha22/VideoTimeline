package com.classting.view.presenter

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.classting.Navigator
import com.classting.R
import com.classting.adapter.contract.FeedAdapterContract
import com.classting.listener.OnItemClickListener
import com.classting.log.Logger
import com.classting.model.Feed
import com.classting.util.CalculateVideoVisibility
import com.classting.view.ListItemView
import com.classting.view.contract.MainContract

/**
 * Created by DavidHa on 2017. 9. 6..
 */

class MainPresenter : MainContract.Presenter, OnItemClickListener, RecyclerView.OnScrollListener() {
    lateinit var context: Context
    lateinit var presenter: MainPresenter
    private lateinit var calculateVideoVisibility: CalculateVideoVisibility
    var adapterModel: FeedAdapterContract.Model? = null
        set(value) {
            field = value
            calculateVideoVisibility = CalculateVideoVisibility(value!!)
        }

    var adapterView: FeedAdapterContract.View? = null
        set(value) {
            field = value
            value?.setOnItemClickListener(this)
        }


    // feed scroll state 가 변경됬을 경우
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            val topPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val bottomPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            if (recyclerView.layoutManager is LinearLayoutManager) {
                calculateVideoVisibility.onScrollStateChanged(topPosition, bottomPosition)
            }
        }
    }

    //  feed scrolled
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val topPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val bottomPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            calculateVideoVisibility.onScrolled(topPosition, bottomPosition)
        }
    }


    /**
     * 비디오 상세화면(VideoDetailFragment)에서 재생 정보를 받아와서 videoView update
     * onResume() 보다 먼저 실행
     */
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == Navigator.VIDEO_DETAIL && resultCode == Activity.RESULT_OK) {
            Logger.v("on activity result")
            val time: Long = data.getLongExtra("positionMs", 0) // video 상세페이지 재생 time
            val position: Int = data.getIntExtra("position", 0) // 해당 리스트 position
            adapterModel?.getItem(position)?.view?.playVideo(time)
            calculateVideoVisibility.curPlayingVideoPos = position
        }
    }

    // onResume 일 경우 Video Play 재생
    override fun resumeVideo() {
        calculateVideoVisibility.playVideo()
    }

    // onPause 일 경우 Video Play 중단
    override fun pauseVideo() {
        calculateVideoVisibility.pauseVideo()
    }

    // onDestroy 일 경우 모든 작업 중단
    override fun destroyVideo() {
        calculateVideoVisibility.destroyVideo()
    }


    // feed list item 클릭했을 경우
    override fun onItemClick(view: View, position: Int) {
        val videoURL = adapterModel?.getItem(position)?.videoURL
        val feedId = adapterModel?.getItem(position)?.id
        if (videoURL != null) {
            if ((view as ListItemView).isVideoEnded()) {  // 완료된 비디오를 누를경우 처음부터 재생
                view.restartVideo()
                calculateVideoVisibility.curPlayingVideoPos = position
            } else {   // video item 클릭했을 경우 상세페이지로 이동
                view.pauseVideo()
                Navigator.goVideoDetail(context, feedId ?: -1, position, videoURL, view.getVideoCurrentTime(), view.isRecorded())
            }
        }
    }


    /**
     * Dummy Data Feed(id, userName, text, photoURL, videoURL)
     */
    fun addDummyData() {
        adapterModel?.addItem(Feed(0, "이름0", "text 만 있음"))
        adapterModel?.addItem(Feed(1, "이름1", "text, image 있음", R.drawable.sample1))
        adapterModel?.addItem(Feed(2, "이름2", "big buck bunny\n스트리밍 비디오", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(3, "이름3", "text, image 있음", R.drawable.sample2))
        adapterModel?.addItem(Feed(4, "이름4", "이시하라 사토미\n가루보 광고", null, "asset:///sample_video1.mp4"))
        adapterModel?.addItem(Feed(5, "이름5", "이시하라 사토미\n도쿄미트로 광고 ", null, "asset:///sample_video2.mp4"))
        adapterModel?.addItem(Feed(6, "이름6", "text, image 있음", R.drawable.sample1))
        adapterModel?.addItem(Feed(7, "이름7", "text 만 있음"))
        adapterModel?.addItem(Feed(8, "이름8", "이시하라 사토미\n술 광고", null, "asset:///sample_video3.mp4"))
        adapterModel?.addItem(Feed(9, "이름9", "text 만 있음"))
        adapterModel?.addItem(Feed(10, "이름10", "이시하라 사토미\n가루보 메이킹영상", null, "asset:///sample_video4.mp4"))
        adapterModel?.addItem(Feed(11, "이름11", "이시하라 사토미\n도자이선 도쿄메트로 광고", null, "asset:///sample_video5.mp4"))
        adapterModel?.addItem(Feed(12, "이름12", "이시하라 사토미\n과주구미 광고", null, "asset:///sample_video6.mp4"))
        adapterModel?.addItem(Feed(13, "이름13", "big buck bunny\n스트리밍 비디오", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(14, "이름14", "text, image 있음", R.drawable.sample2))
    }
}