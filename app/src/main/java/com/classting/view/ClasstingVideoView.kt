package com.classting.view

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.classting.R
import com.classting.log.Logger
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.Timeline
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory
import com.google.android.exoplayer2.source.ExtractorMediaSource
import com.google.android.exoplayer2.source.MediaSource
import com.google.android.exoplayer2.source.hls.HlsMediaSource
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.*
import com.google.android.exoplayer2.util.Util
import kotlinx.android.synthetic.main.classting_vidieo.view.*


/**
 * Created by DavidHa on 2017. 9. 7..
 */
class ClasstingVideoView(context: Context, attributeSet: AttributeSet? = null) : FrameLayout(context, attributeSet) {

    lateinit var player: SimpleExoPlayer
    private var window: Timeline.Window
    private var mediaDataSourceFactory: DataSource.Factory
    private lateinit var trackSelector: DefaultTrackSelector
    private var bandwidthMeter: BandwidthMeter

    init {
        LayoutInflater.from(context).inflate(R.layout.classting_vidieo, this, true)
        bandwidthMeter = DefaultBandwidthMeter();
        mediaDataSourceFactory = DefaultDataSourceFactory(context, Util.getUserAgent(context, "mediaPlayerSample"), bandwidthMeter as TransferListener<in DataSource>)
        window = Timeline.Window()
        initializePlayer()
    }


    private fun initializePlayer() {
        val videoTrackSelectionFactory = AdaptiveTrackSelection.Factory(bandwidthMeter)
        trackSelector = DefaultTrackSelector(videoTrackSelectionFactory)
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
        videoView.player = player
        videoView.useController = false
        player.playWhenReady = false
    }

    fun setData(videoURL: String) {
        val extractorsFactory = DefaultExtractorsFactory()
        val mediaSource = ExtractorMediaSource(Uri.parse(videoURL),
                mediaDataSourceFactory, extractorsFactory, null, null)
        player.prepare(mediaSource)
    }

    fun playVideo() {
        if (!player.playWhenReady) {
            Logger.v("play")
            player.playWhenReady = true
        }

    }

    fun pauseVideo() {
        if (player.playWhenReady) {
            Logger.v("pause")
            player.playWhenReady = false
        }
    }

}