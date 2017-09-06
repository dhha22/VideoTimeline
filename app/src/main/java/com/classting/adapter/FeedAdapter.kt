package com.classting.adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import com.classting.listener.OnItemClickListener
import com.classting.model.Feed
import com.classting.view.ListItemView

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class FeedAdapter(val context: Context) : RecyclerView.Adapter<FeedAdapter.FeedHolder>() {

    private val feeds: List<Feed> = ArrayList()
    lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): FeedHolder {
        return FeedHolder(ListItemView(context))
    }

    override fun onBindViewHolder(holder: FeedHolder, position: Int) {
        holder.v.setData(feeds[position])
    }

    override fun getItemCount(): Int {
        return feeds.size
    }

    inner class FeedHolder(view: View) : RecyclerView.ViewHolder(view) {
        val v: ListItemView by lazy { view as ListItemView }

        init {
            v.setOnClickListener { itemClickListener.onItemClick(it, adapterPosition) }
        }
    }
}