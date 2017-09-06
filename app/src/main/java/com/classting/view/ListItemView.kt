package com.classting.view

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import com.classting.R
import com.classting.model.Feed

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class ListItemView(context: Context, attributeSet: AttributeSet? = null) : CardView(context, attributeSet) {
    init {
        LayoutInflater.from(context).inflate(R.layout.list_item, this, false)
    }

    fun setData(feed: Feed) {

    }
}