package com.classting.adapter.contract

import com.classting.listener.OnItemClickListener
import com.classting.model.Feed

/**
 * Created by DavidHa on 2017. 9. 6..
 */
interface FeedAdapterContract {
    interface View {
         fun setOnItemClickListener(onItemClickListener: OnItemClickListener)
    }

    interface Model {
        fun getItem(position : Int) : Feed
        fun addItem(feed : Feed)
        fun clearItem()
    }
}