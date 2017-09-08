package com.classting.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import com.classting.listener.VideoPlayState

/**
 * Created by DavidHa on 2017. 9. 8..
 */
abstract class Holder(view:View) : RecyclerView.ViewHolder(view) {
    abstract var v : VideoPlayState
}