package com.classting.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.classting.adapter.contract.FeedAdapterContract
import com.classting.listener.OnItemClickListener
import com.classting.listener.VideoPlayState
import com.classting.log.Logger
import com.classting.model.Feed
import com.classting.view.ListItemView
import rx.subjects.PublishSubject

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class FeedAdapter(val context: Context) : RecyclerView.Adapter<FeedAdapter.FeedHolder>(), FeedAdapterContract.Model, FeedAdapterContract.View{
    private val feeds: ArrayList<Feed> = ArrayList()
    private lateinit var itemClickListener: OnItemClickListener
    private val videoPositionSubject = PublishSubject.create<Long>()

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

    override fun setContinuePlay(positionMs: Long) {
        videoPositionSubject.onNext(positionMs)
    }

    override fun getItem(position: Int): Feed {
        return feeds[position]
    }

    override fun addItem(feed: Feed) {
        feeds.add(feed)
        notifyDataSetChanged()
    }

    override fun clearItem() {
        feeds.clear()
        notifyDataSetChanged()
    }


    inner class FeedHolder(view: View) : Holder(view) {
        override var v: VideoPlayState = view as ListItemView

        init {
            videoPositionSubject.subscribe {
                v.playVideo(it)
            }
            (v as ListItemView).setOnClickListener { itemClickListener.onItemClick(it, adapterPosition) }
        }
    }
}