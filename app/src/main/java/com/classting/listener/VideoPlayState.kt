package com.classting.listener

/**
 * Created by DavidHa on 2017. 9. 6..
 */
interface VideoPlayState {
    fun getVisibilityPercent() : Int
    fun playVideo()
    fun pauseVideo()
    fun setPercent(percent:Int)
    fun setContinuePlay(positionMs : Long)
}