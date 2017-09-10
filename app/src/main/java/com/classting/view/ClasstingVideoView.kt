package com.classting.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.classting.R
import com.classting.log.Logger
import com.classting.model.Feed
import com.classting.util.FileUtil
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.TrackGroupArray
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.trackselection.TrackSelectionArray
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.classting_vidieo.view.*
import rx.Observable
import rx.Subscription
import rx.schedulers.Schedulers
import rx.subjects.PublishSubject
import java.util.concurrent.TimeUnit
import kotlin.properties.Delegates


/**
 * Created by DavidHa on 2017. 9. 7..
 */
class ClasstingVideoView(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet), Player.EventListener {

    lateinit var player: SimpleExoPlayer
    private var mediaDataSourceFactory: DataSource.Factory
    private lateinit var trackSelector: DefaultTrackSelector
    private var bandwidthMeter: BandwidthMeter
    private var feedId by Delegates.notNull<Long>()
    private lateinit var videoURL: String
    private var isEnd: Boolean = false
    private var playStateSubject: PublishSubject<Boolean>? = null
    private var timerSubscription: Subscription? = null
    var isRecorded : Boolean = false

    init {
        LayoutInflater.from(context).inflate(R.layout.classting_vidieo, this, true)
        bandwidthMeter = DefaultBandwidthMeter()
        mediaDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "mediaPlayerSample"), bandwidthMeter as TransferListener<in DataSource>)
        initializePlayer()
    }


    private fun initializePlayer() {
        Logger.v("initialize player")
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        videoView.player = player
        videoView.useController = false
        player.playWhenReady = false
        player.addListener(this)
    }

    fun setData(feedId: Long, videoURL: String) {
        this.feedId = feedId
        this.videoURL = videoURL
        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource = ExtractorMediaSource(Uri.parse(videoURL),
                mediaDataSourceFactory, extractorsFactory, null, null)
        player.prepare(mediaSource)
    }

    fun showController() {
        videoView.useController = true
        videoView.showController()
    }

    fun playVideo() {
        if (!player.playWhenReady) {
            player.playWhenReady = true
            Logger.v("play video playWhenReady: " + player.playWhenReady)
        }
    }

    fun pauseVideo() {
        if (player.playWhenReady) {
            player.playWhenReady = false
            Logger.v("pause video playWhenReady: " + player.playWhenReady)
        }
    }

    fun release() {
        player.release()
    }

    fun getCurrentTime(): Long {
        return player.currentPosition
    }

    fun continuePlay(positionMs: Long) {
        player.seekTo(positionMs)
    }

    fun isPlaying(): Boolean {
        return player.playWhenReady
    }

    fun isVideoEnded(): Boolean {
        return isEnd
    }

    fun setPlayerState(playStateSubject: PublishSubject<Boolean>) {
        this.playStateSubject = playStateSubject
    }

    override fun onPlayerStateChanged(playWhenReady: Boolean, playbackState: Int) {
        Logger.v("feed id: $feedId, playWhenReady: $playWhenReady, playbackState: $playbackState")
        player.playWhenReady = playWhenReady
        when (playbackState) {
            Player.STATE_READY -> {
                if (player.playWhenReady && !isRecorded) {   // playing  앱 시작하고나서 최초로 한번만 기록
                    timerSubscription = Observable.timer(3, TimeUnit.SECONDS)
                            .observeOn(Schedulers.io())
                            .subscribe({ FileUtil.recordVideoInfo(context.filesDir, feedId) }, { Logger.e(it) }, {
                                isRecorded = true
                                timerSubscription = null
                            })
                } else {  // pause
                    timerSubscription?.unsubscribe()
                }
                isEnd = false
            }

            Player.STATE_ENDED -> { // video is end
                Logger.v("video is end")
                playStateSubject?.onNext(false)
                isEnd = true
            }
        }
        playStateSubject?.onNext(player.playWhenReady)
    }

    override fun onPlaybackParametersChanged(playbackParameters: PlaybackParameters?) {
    }

    override fun onTracksChanged(trackGroups: TrackGroupArray?, trackSelections: TrackSelectionArray?) {
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
    }

    override fun onLoadingChanged(isLoading: Boolean) {
    }

    override fun onPositionDiscontinuity() {
    }

    override fun onRepeatModeChanged(repeatMode: Int) {
    }

    override fun onTimelineChanged(timeline: Timeline?, manifest: Any?) {
    }

}