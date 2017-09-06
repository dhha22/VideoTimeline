package com.classting.model

/**
 * Created by DavidHa on 2017. 9. 6..
 */
data class Feed(val id: Long,
                val user: User,
                val text : String,
                val photos: List<Photo>? = null,
                val video: Video? = null)