package com.dhha22.videotimeline.adapter.contract

import com.dhha22.videotimeline.listener.OnItemClickListener
import com.dhha22.videotimeline.model.Feed

/**
 * Created by DavidHa on 2017. 9. 6..
 */
interface FeedAdapterContract {
    interface View {
        fun setOnItemClickListener(onItemClickListener: OnItemClickListener)
    }

    interface Model {
        fun getItem(position: Int): Feed
        fun addItem(feed: Feed)
    }
}