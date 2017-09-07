package com.classting.view.presenter

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import com.classting.R
import com.classting.adapter.FeedAdapter
import com.classting.adapter.contract.FeedAdapterContract
import com.classting.listener.OnItemClickListener
import com.classting.log.Logger
import com.classting.model.Feed
import com.classting.view.contract.MainContract

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class MainPresenter : MainContract.Presenter, OnItemClickListener, RecyclerView.OnScrollListener() {
    lateinit var presenter: MainPresenter
    lateinit var adapterModel: FeedAdapterContract.Model
    var adapterView: FeedAdapterContract.View? = null
        set(value) {
            field = value
            value?.setOnItemClickListener(this)
        }


    // feed scroll state
    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCROLL_STATE_IDLE) {
            if (recyclerView.layoutManager is LinearLayoutManager) {
                //Logger.v("find first visible item position: " + (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition())
                //Logger.v("find last visible item position: " + (recyclerView.layoutManager as LinearLayoutManager).findLastVisibleItemPosition())

            }
        }
    }

    //  feed scrolled
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        if (recyclerView.layoutManager is LinearLayoutManager) {
            val position = (recyclerView.layoutManager as LinearLayoutManager).findFirstVisibleItemPosition()
            val holder = (recyclerView.findViewHolderForAdapterPosition(position) as FeedAdapter.FeedHolder)
            Logger.v("view visible percent: " + holder.v.getVisibilityPercent())
        }
    }

    // feed list item 클릭했을 경우
    override fun onItemClick(view: View, position: Int) {

    }

    // make dummy data 10개
    fun addDummyData() {
        adapterModel.addItem(Feed(0, "이름0", "text 만 있음"))
        adapterModel.addItem(Feed(1, "이름1", "text, image 있음", R.drawable.sample1))
        adapterModel.addItem(Feed(2, "이름2", "text 만 있음"))
        adapterModel.addItem(Feed(3, "이름3", "text, image 있음", R.drawable.sample2))
        adapterModel.addItem(Feed(4, "이름4", "text 만 있음"))
        adapterModel.addItem(Feed(5, "이름5", "text 만 있음"))
        adapterModel.addItem(Feed(6, "이름6", "text 만 있음", R.drawable.sample1))
        adapterModel.addItem(Feed(7, "이름7", "text 만 있음", R.drawable.sample2))
        adapterModel.addItem(Feed(8, "이름8", "text 만 있음"))
        adapterModel.addItem(Feed(9, "이름9", "text 만 있음"))

    }
}