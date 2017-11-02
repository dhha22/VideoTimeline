package com.dhha22.videotimeline.util

import com.dhha22.videotimeline.adapter.contract.FeedAdapterContract
import com.dhha22.videotimeline.listener.VideoPlayState
import com.dhha22.videotimeline.log.Logger
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class CalculateVideoVisibility(val adapterModel: FeedAdapterContract.Model) {

    var curPlayingVideoPos: Int = -1
    private var topPosition: Int = 0
    private var bottomPosition: Int = 0
    private lateinit var subscription: Subscription


    /**
     * 사용자가 스크롤을 멈출 때 마다 화면에 보이는 View Visibility 값을 계산한다
     * @param topPosition : recyclerView 화면에 보이는 상단 position
     * @param bottomPosition : recyclerView 화면에 보이는 하단 position
     */
    fun onScrollStateChanged(topPosition: Int, bottomPosition: Int) {
        Logger.v("top position: $topPosition , bottom position: $bottomPosition")

        if (curPlayingVideoPos !in topPosition..bottomPosition) {
            pauseVideo()
        }

        subscription = Observable
                .range(topPosition, bottomPosition - topPosition + 1)   // range (n,m) = n ~ n+m-1
                .filter { adapterModel.getItem(it).videoURL != null && adapterModel.getItem(it).view != null }   // 비디오 동영상일 경우만 확인
                .subscribeOn(Schedulers.computation())
                .subscribe({ setVideoPlayState(adapterModel.getItem(it).view!!, it) }, { Logger.e(it) })
    }

    /**
     * 사용자가 스크롤을 할때 현재 재생되고있는 비디오가 화면에 보이지 않을 경우 pauseVideo()
     * @param topPosition : recyclerView 화면에 보이는 상단 position
     * @param bottomPosition : recyclerView 화면에 보이는 하단 position
     */
    fun onScrolled(topPosition: Int, bottomPosition: Int) {
        this.topPosition = topPosition
        this.bottomPosition = bottomPosition
        if (curPlayingVideoPos !in topPosition..bottomPosition) {
            pauseVideo()
        }
    }

    /**
     * 화면의 visibility percent 에 따라 video play state 값이 변함
     * @param view : 해당 리스트 아이템 비디오 뷰
     * @param position : 현재 recyclerView position
     */
    private fun setVideoPlayState(view: VideoPlayState, position: Int) {
        val percent: Int = view.getVisibilityPercent() // 해당 포지션값의 visible percent 를 구함
        Logger.v("position: $position, curPlayingVideoPos: $curPlayingVideoPos, cur view percent: $percent")

        // 화면에 비디오가 50% 이상 보일 경우 && 화면에 두개이상 동영상이 있을경우 상위 위치한 동영상만 play
        if (percent >= 50 && (curPlayingVideoPos == -1 || position <= curPlayingVideoPos)) {
            curPlayingVideoPos = position
            Logger.v("curPlayVideoPos: $curPlayingVideoPos")
            view.playVideo()
        } else {
            if (position == curPlayingVideoPos) curPlayingVideoPos = -1
            Logger.v("curPlayVideoPos: $curPlayingVideoPos")
            view.pauseVideo()
        }
    }

    fun pauseVideo() {
        if (curPlayingVideoPos != -1) {
            Logger.v("pause pos: $curPlayingVideoPos" )
            adapterModel.getItem(curPlayingVideoPos).view!!.pauseVideo()
            curPlayingVideoPos = -1
        }
    }

    fun playVideo() {
        if (curPlayingVideoPos != -1) {
            Logger.v("play video: $curPlayingVideoPos")
            adapterModel.getItem(curPlayingVideoPos).view!!.playVideo()
        }
    }

    fun destroyVideo() {
        subscription.unsubscribe()
    }

}