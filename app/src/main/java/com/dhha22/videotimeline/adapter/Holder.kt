package com.dhha22.videotimeline.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.dhha22.videotimeline.listener.VideoPlayState

/**
 * Created by DavidHa on 2017. 9. 8..
 */
abstract class Holder(view:View) : RecyclerView.ViewHolder(view) {
    abstract var v : VideoPlayState
}