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


    // feed scroll state
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (recyclerView.layoutManager is LinearLayoutManager) {
            }
        }
    }

    //  feed scrolled
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val topPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val bottomPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            calculateVideoVisibility.onScroll(topPosition, bottomPosition)
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


    // feed list item 클릭했을 경우
    override fun onItemClick(view: View, position: Int) {
        val videoURL = adapterModel?.getItem(position)?.videoURL
        if (videoURL != null) {    // video item 클릭했을 경우 상세페이지로 이동
            (view as ListItemView).pauseVideo()
            Navigator.goVideoDetail(context, position, videoURL, view.getVideoCurrentTime())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == Navigator.VIDEO_DETAIL && resultCode == Activity.RESULT_OK) {
            Logger.v("on activity result")
            val time: Long = data.getLongExtra("positionMs", 0) // video 상세페이지 재생 time
            val position : Int = data.getIntExtra("position", 0)
            adapterModel?.getItem(position)?.view?.playVideo(time)
        }
    }

    // make dummy data 20개
    fun addDummyData() {
        adapterModel?.addItem(Feed(0, "이름0", "text 만 있음"))
        adapterModel?.addItem(Feed(1, "이름1", "text, image 있음", R.drawable.sample1))
        adapterModel?.addItem(Feed(2, "이름2", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(3, "이름3", "text, image 있음", R.drawable.sample2))
        adapterModel?.addItem(Feed(4, "이름4", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(5, "이름5", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(6, "이름6", "text, image 있음", R.drawable.sample1))
        adapterModel?.addItem(Feed(7, "이름7", "text 만 있음"))
        adapterModel?.addItem(Feed(8, "이름8", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(9, "이름9", "text 만 있음"))
        adapterModel?.addItem(Feed(10, "이름10", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(11, "이름11", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(12, "이름12", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(13, "이름13", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(14, "이름14", "text, image 있음", R.drawable.sample2))
        adapterModel?.addItem(Feed(15, "이름15", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(16, "이름16", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(17, "이름17", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(18, "이름18", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel?.addItem(Feed(19, "이름19", "text, image 있음", R.drawable.sample1))
    }
}