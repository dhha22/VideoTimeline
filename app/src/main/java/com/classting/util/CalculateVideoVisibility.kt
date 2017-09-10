package com.classting.util

import android.support.v7.widget.RecyclerView
import android.view.View
import com.classting.adapter.FeedAdapter
import com.classting.adapter.Holder
import com.classting.adapter.contract.FeedAdapterContract
import com.classting.listener.VideoPlayState
import com.classting.log.Logger
import com.classting.model.TempVideoInfo
import rx.Observable
import rx.Scheduler
import rx.Subscription
import rx.schedulers.Schedulers
import java.util.concurrent.TimeUnit

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class CalculateVideoVisibility(val adapterModel: FeedAdapterContract.Model) {

    private var curPlayingVideoPos: Int = -1
    private var topPosition: Int = 0
    private var bottomPosition: Int = 0
    private lateinit var subscription : Subscription


    /**
     * 사용자가 스크롤을 할때마다 화면에 보이는 View Visibility 값을 계산한다
     * @param topPosition : recyclerView 화면에 보이는 상단 position
     * @param bottomPosition : recyclerView 화면에 보이는 하단 position
     */

    fun onScroll(topPosition: Int, bottomPosition: Int) {
        this.bottomPosition = bottomPosition
        if (curPlayingVideoPos < topPosition) pauseVideo() // 현재 재생 포지션값이 화면에 보이지 않을 경우
        if(curPlayingVideoPos in topPosition .. bottomPosition) this.bottomPosition = curPlayingVideoPos
        Logger.v("top position: $topPosition , bottom position: $bottomPosition")
       subscription = Observable
                .range(topPosition, this.bottomPosition - topPosition + 1)   // range (n,m) = n ~ n+m-1
                .filter { adapterModel.getItem(it).videoURL != null && adapterModel.getItem(it).view != null }   // 비디오 동영상일 경우만 확인
                .subscribeOn(Schedulers.computation())
                .subscribe({ setPlayVideo(adapterModel.getItem(it).view!!, it) }, { Logger.e(it) })
    }

    private fun setPlayVideo(view: VideoPlayState, position: Int) {
        val percent: Int = view.getVisibilityPercent() // 해당 포지션값의 visible percent를 구함
        Logger.v("position: $position, curPlayingVideoPos: $curPlayingVideoPos, cur view percent: $percent")
        if (percent >= 50 && (curPlayingVideoPos == -1 || position <= curPlayingVideoPos)) {
            if (curPlayingVideoPos != position) {
                curPlayingVideoPos = position
                view.playVideo()
            }
        } else {
            if (topPosition == position && curPlayingVideoPos > topPosition) subscription.unsubscribe()
            if (position == curPlayingVideoPos) curPlayingVideoPos = -1
            view.pauseVideo()
        }
    }

    fun pauseVideo() {
        if (curPlayingVideoPos != -1) {
            adapterModel.getItem(curPlayingVideoPos).view!!.pauseVideo()
            curPlayingVideoPos = -1
        }
    }

    fun playVideo() {
        if (curPlayingVideoPos != -1) {
            adapterModel.getItem(curPlayingVideoPos).view!!.playVideo()
        }
    }

}