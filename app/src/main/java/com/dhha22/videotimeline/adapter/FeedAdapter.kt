package com.dhha22.videotimeline.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.dhha22.videotimeline.adapter.contract.FeedAdapterContract
import com.dhha22.videotimeline.listener.OnItemClickListener
import com.dhha22.videotimeline.listener.VideoPlayState

import com.dhha22.videotimeline.model.Feed
import com.dhha22.videotimeline.view.ListItemView

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class FeedAdapter(val context: Context) : RecyclerView.Adapter<FeedAdapter.FeedHolder>(),
        FeedAdapterContract.Model, FeedAdapterContract.View {

    private val feeds: ArrayList<Feed> = ArrayList()
    private lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedHolder {
        return FeedHolder(ListItemView(context))
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        (holder.v as ListItemView).setData(feeds[position])
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    override fun setOnItemClickListener(onItemClickListener: OnItemClickListener) {
        this.itemClickListener = onItemClickListener
    }

    override fun getItem(position: Int): Feed {
        return feeds[position]
    }

    override fun addItem(feed: Feed) {
        feeds.add(feed)
        notifyDataSetChanged()
    }


    inner class FeedHolder(view: View) : Holder(view) {
        override var v: VideoPlayState = view as ListItemView

        init {
            (v as ListItemView).setOnClickListener { itemClickListener.onItemClick(it, adapterPosition) }
        }
    }
}