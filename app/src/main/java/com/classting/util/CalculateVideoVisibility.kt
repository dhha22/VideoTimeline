package com.classting.util

import android.support.v7.widget.RecyclerView
import com.classting.adapter.FeedAdapter
import com.classting.adapter.Holder
import com.classting.log.Logger
import com.classting.model.TempVideoInfo
import rx.Observable

/**
 * Created by DavidHa on 2017. 9. 8..
 */
class CalculateVideoVisibility {

    private var curPlayingVideoPos: Int? = null

    fun onScroll(recyclerView: RecyclerView, topPosition: Int, bottomPosition: Int, filter: (position: Int) -> Boolean) {
        Observable.range(topPosition, bottomPosition)   // range (topPosition ~ bottomPosition)
                .filter { filter.invoke(it) }   // 비디오 동영상일 경우만 확인
                .map { TempVideoInfo(it, getHolder(recyclerView, it).v.getVisibilityPercent()) }    // 해당 포지션값의 visible percent를 구함
                .subscribe({ setPlayVideo(recyclerView, it.position, it.percent) }, {}, { })
    }

    private fun setPlayVideo(recyclerView: RecyclerView, position: Int, percent: Int) {
        val holder = getHolder(recyclerView, position)
        Logger.v("position: $position, percent: $percent")
        if (percent >= 50) {    // visible 50퍼센트 이상
            if (curPlayingVideoPos == null || position == curPlayingVideoPos) {
                holder.v.playVideo()
                curPlayingVideoPos = position
            }
        } else {
            if (position == curPlayingVideoPos) curPlayingVideoPos = null
            holder.v.pauseVideo()
        }
        holder.v.setPercent(percent)
    }

    private fun getHolder(recyclerView: RecyclerView, position: Int): Holder {
        return recyclerView.findViewHolderForAdapterPosition(position) as Holder
    }


}