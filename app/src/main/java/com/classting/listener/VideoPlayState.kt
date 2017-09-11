package com.classting.listener

/**
 * Created by DavidHa on 2017. 9. 6..
 */
interface VideoPlayState {
    fun getVisibilityPercent(): Int
    fun playVideo(positionMs: Long = 0)
    fun pauseVideo()
    fun setPercent(percent: Int)
    fun setRecorded(isRecorded: Boolean)
}