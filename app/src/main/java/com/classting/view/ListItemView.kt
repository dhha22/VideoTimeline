package com.classting.view

import android.content.Context
import android.graphics.Rect
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
import rx.Observable
import rx.android.schedulers.AndroidSchedulers
import rx.functions.Action1
import rx.schedulers.Schedulers

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class ListItemView(context: Context, attributeSet: AttributeSet? = null)
    : CardView(context, attributeSet), VideoPlayState {
    private val videoRect: Rect = Rect()
    private lateinit var feed: Feed
    init {
        LayoutInflater.from(context).inflate(R.layout.list_item, this, true)
        useCompatPadding = true
    }

    fun setData(feed: Feed) {
        this.feed = feed
        Picasso.with(context).load(R.drawable.profile).fit().centerCrop().into(profileImage)
        nameTxt.text = feed.userName
        contentTxt.text = feed.text

        if (feed.photoURL != null) {
            photo.visibility = View.VISIBLE
            Picasso.with(context).load(feed.photoURL).fit().centerCrop().into(photo)
        } else {
            photo.visibility = View.GONE
        }

        if (feed.videoURL != null) {
            classtingVideoView.setData(feed.videoURL)
            classtingVideoView.visibility = View.VISIBLE
        } else {
            classtingVideoView.visibility = View.GONE
        }
    }

    fun setPercent(percent: Int) {
        percentTxt.text = String.format("%d", percent)
    }

    override fun getVisibilityPercent(): Int {
        var percent: Int = 0
        classtingVideoView.getLocalVisibleRect(videoRect)   // 현재 Video View의 위치를 가져옴
        //Logger.v("rect top: " + videoRect.top + ", rect bottom: " + videoRect.bottom + ", view height: " + classtingVideoView.height)
        if (videoRect.bottom == classtingVideoView.height) {
            percent = (videoRect.bottom - videoRect.top) * 100 / videoRect.bottom   // first item
        } else if ((videoRect.top == 0)) {
            percent = (videoRect.bottom) * 100 / classtingVideoView.height    // last item
        }
        return percent
    }


    override fun playVideo() {
        if (feed.videoURL != null) {
            classtingVideoView.playVideo()
        }
    }

    override fun pauseVideo() {
        if (feed.videoURL != null) {
            classtingVideoView.pauseVideo()
        }
    }
}