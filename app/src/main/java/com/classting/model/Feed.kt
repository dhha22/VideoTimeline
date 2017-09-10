package com.classting.model

import com.classting.listener.VideoPlayState

/**
 * Created by DavidHa on 2017. 9. 6..
 */
data class Feed(val id: Long,
                val userName: String,
                val text: String,
                val photoURL: Int? = null,
                val videoURL: String? = null,
                var playingTime:Long = 0,
                var view : VideoPlayState? = null)