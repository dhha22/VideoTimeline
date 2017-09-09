package com.classting.util

import android.support.v7.widget.RecyclerView
import com.classting.adapter.Holder
import com.classting.log.Logger
import com.classting.model.TempVideoInfo
import rx.Observable
import rx.Scheduler
import rx.schedulers.Schedulers

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class CalculateVideoVisibility {

    private var curPlayingVideoPos: Int = -1
    private var topPosition: Int = 0
    private var bottomPosition: Int = 0

    fun onScroll(recyclerView: RecyclerView, topPosition: Int, bottomPosition: Int, filter: (position: Int) -> Boolean) {
        this.topPosition = topPosition  // recyclerView 화면에 보이는 상단 position
        this.bottomPosition = bottomPosition    //  recyclerView 화면에 보이는 하단 position
        Logger.v("top position: $topPosition , bottom position: $bottomPosition")

        Observable.range(topPosition, bottomPosition - topPosition + 1)   // range (n,m) = n ~ n+m-1
                .filter { filter.invoke(it) }   // 비디오 동영상일 경우만 확인
                .subscribeOn(Schedulers.computation())
                .subscribe({ setPlayVideo(recyclerView, it) }, { Logger.e(it) })
    }

    private fun setPlayVideo(recyclerView: RecyclerView, position: Int) {
        Logger.v("position: $position, curPlayingVideoPos: $curPlayingVideoPos")
        if (curPlayingVideoPos < topPosition) curPlayingVideoPos = -1   // 현재 재생 포지션값이 화면에 보이지 않을 경우
        val holder: Holder = getHolder(recyclerView, position)
        val percent: Int? = holder.v.getVisibilityPercent() // 해당 포지션값의 visible percent를 구함
        if (percent != null) {
            if (percent >= 50 && (curPlayingVideoPos == -1 || position <= curPlayingVideoPos)) {
                holder.v.playVideo()
                curPlayingVideoPos = position
            } else {
                if (position == curPlayingVideoPos) curPlayingVideoPos = -1
                holder.v.pauseVideo()
            }
        }
    }

    fun pauseVideo(recyclerView: RecyclerView) {
        if (curPlayingVideoPos != -1) {
            getHolder(recyclerView, curPlayingVideoPos).v.pauseVideo()
            curPlayingVideoPos = -1
        }
    }

    fun playVideo(recyclerView: RecyclerView) {
        if(curPlayingVideoPos != -1) {
            getHolder(recyclerView, curPlayingVideoPos).v.playVideo()
        }
    }

    private fun getHolder(recyclerView: RecyclerView, position: Int): Holder {
        Logger.v("holder position: " + position)
        return recyclerView.findViewHolderForAdapterPosition(position) as Holder
    }


}