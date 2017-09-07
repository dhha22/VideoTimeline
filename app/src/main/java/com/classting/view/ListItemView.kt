package com.classting.view

import android.content.Context
import android.support.v7.widget.CardView
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.classting.R
import com.classting.listener.VideoPlayState
import com.classting.log.Logger
import com.classting.model.Feed
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.list_item.view.*

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class ListItemView(context: Context, attributeSet: AttributeSet? = null)
    : CardView(context, attributeSet), VideoPlayState {
    init {
        LayoutInflater.from(context).inflate(R.layout.list_item, this, true)
        useCompatPadding = true
    }

    fun setData(feed: Feed) {
        Picasso.with(context).load(R.drawable.profile).fit().centerCrop().into(profileImage)
        nameTxt.text = feed.userName
        contentTxt.text = feed.text

        if (feed.photoURL != null) {
            photo.visibility = View.VISIBLE
            Picasso.with(context).load(feed.photoURL).fit().centerCrop().into(photo)
        } else {
            photo.visibility = View.GONE
        }
    }

    override fun playVideo() {
        Logger.v("play video")
    }

    override fun pauseVideo() {
        Logger.v("pause video")
    }
}