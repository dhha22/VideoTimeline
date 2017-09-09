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
    lateinit var adapterModel: FeedAdapterContract.Model
    private val calculateVideoVisibility: CalculateVideoVisibility = CalculateVideoVisibility()

    var adapterView: FeedAdapterContract.View? = null
        set(value) {
            field = value
            value?.setOnItemClickListener(this)
        }


    // feed scroll state
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (recyclerView.layoutManager is LinearLayoutManager) {
               /* val topPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
                val bottomPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
                Logger.v("scroll state idle")
                calculateVideoVisibility.onScroll(recyclerView, topPosition, bottomPosition, { adapterModel.getItem(it).videoURL != null })
            */}
        }
    }

    //  feed scrolled
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val topPosition = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val bottomPosition = (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition()
            calculateVideoVisibility.onScroll(recyclerView, topPosition, bottomPosition, { adapterModel.getItem(it).videoURL != null })
        }
    }

    // onPause 일 경우 Video Play 중단
    override fun pauseVideo(recyclerView: RecyclerView) {
        calculateVideoVisibility.pauseVideo(recyclerView)
    }


    // feed list item 클릭했을 경우
    override fun onItemClick(view: View, position: Int) {
        val videoURL = adapterModel.getItem(position).videoURL
        if (videoURL != null) {    // video item 클릭했을 경우 상세페이지로 이동
            (view as ListItemView).pauseVideo()
            Navigator.goVideoDetail(context, videoURL, view.getVideoCurrentPosition())
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == Navigator.VIDEO_DETAIL && resultCode == Activity.RESULT_OK) {
            Logger.v("on activity result")
            val time: Long = data.getLongExtra("positionMs", 0) // video 상세페이지 재생 time
            adapterView?.setContinuePlay(time)
        }
    }

    // make dummy data 10개
    fun addDummyData() {
        adapterModel.addItem(Feed(0, "이름0", "text 만 있음"))
        adapterModel.addItem(Feed(1, "이름1", "text, image 있음", R.drawable.sample1))
        adapterModel.addItem(Feed(2, "이름2", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel.addItem(Feed(3, "이름3", "text, image 있음", R.drawable.sample2))
        adapterModel.addItem(Feed(4, "이름4", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel.addItem(Feed(5, "이름5", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel.addItem(Feed(6, "이름6", "text, image 있음", R.drawable.sample1))
        adapterModel.addItem(Feed(7, "이름7", "text 만 있음"))
        adapterModel.addItem(Feed(8, "이름8", "text, video 있음", null, "http://clips.vorwaerts-gmbh.de/big_buck_bunny.mp4"))
        adapterModel.addItem(Feed(9, "이름9", "text 만 있음"))
    }
}