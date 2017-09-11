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
import rx.android.schedulers.AndroidSchedulers
import rx.subjects.PublishSubject

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class ListItemView(context: Context, attributeSet: AttributeSet? = null)
    : CardView(context, attributeSet), VideoPlayState {
    private val videoRect: Rect = Rect()
    private lateinit var feed: Feed
    private val playStateSubject = PublishSubject.create<Boolean>()

    init {
        LayoutInflater.from(context).inflate(R.layout.list_item, this, true)
        useCompatPadding = true
        playStateSubject
                .distinctUntilChanged()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ videoCoverState(it) }, { Logger.e(it) })
        classtingVideoView.setPlayerState(playStateSubject)
    }

    fun setData(feed: Feed) {
        this.feed = feed
        feed.view = this
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
            classtingVideoView.setData(feed.id, feed.videoURL)
            videoLayout.visibility = View.VISIBLE
            classtingVideoView.visibility = View.VISIBLE
        } else {
            videoLayout.visibility = View.GONE
            classtingVideoView.visibility = View.GONE
        }
    }


    // 테스트 뷰 (view visibility percent)
    override fun setPercent(percent: Int) {
        percentTxt.text = String.format("%d", percent)
    }

    /**
     * 현재 보이는 video view 위치를 계산하여 videoRect 에 저장함
     * videoRect.bottom 과 video view 의 height 과 같으면 리스트 화면에 보이는 가장 상단 view
     * videoRect.top 이 0일 경우 리스트 화면에 보이는 가장 하단 view
     */
    override fun getVisibilityPercent(): Int {
        var percent: Int = 0
        classtingVideoView.getLocalVisibleRect(videoRect)
        //Logger.v("rect top: " + videoRect.top + ", rect bottom: " + videoRect.bottom + ", view height: " + classtingVideoView.height)
        if (videoRect.bottom == classtingVideoView.height) {
            percent = (videoRect.bottom - videoRect.top) * 100 / videoRect.bottom   // first item
        } else if ((videoRect.top == 0)) {
            percent = (videoRect.bottom) * 100 / classtingVideoView.height    // last item
        }
        return percent
    }

    // video play 상태에 따라 video cover 의 상태가 변함
    private fun videoCoverState(isPlaying: Boolean) {
        Logger.v("playWhenReady: $isPlaying")
        if (isPlaying) {
            videoCover.visibility = View.GONE
        } else {
            videoCover.visibility = View.VISIBLE
        }
    }

    /**
     * video 를 재생 시킨다.
     * 재생시간이 있을 경우 이어서 video 재생
     */
    override fun playVideo(positionMs: Long) {
        if (feed.videoURL != null && !classtingVideoView.isPlaying()) {
            if (positionMs > 0) feed.playingTime = positionMs   // video detail 재생시간 업데이트
            Logger.v("get playing time: " + feed.playingTime)
            setContinuePlay(feed.playingTime)
            classtingVideoView.playVideo()
        }
    }

    /**
     * video 를 일시정지 시킨다.
     * 해당 video 가 플레이되고 있는 재생시간을 저장한뒤 일시 정지
     */
    override fun pauseVideo() {
        if (feed.videoURL != null && classtingVideoView.isPlaying()) {
            feed.playingTime = getVideoCurrentTime()
            Logger.v("save playing time: " + feed.playingTime)
            classtingVideoView.pauseVideo()
        }
    }

    fun restartVideo() {
        classtingVideoView.restartVideo()
    }

    fun getVideoCurrentTime(): Long {
        return classtingVideoView.getCurrentTime()
    }

    private fun setContinuePlay(positionMs: Long) {
        classtingVideoView.continuePlay(positionMs)
    }

    fun isVideoEnded(): Boolean {
        return classtingVideoView.isVideoEnded()
    }

    fun isRecorded() : Boolean{
        return classtingVideoView.isRecorded
    }

}