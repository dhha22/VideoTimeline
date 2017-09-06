package com.classting.view.presenter

import android.view.View
import com.classting.adapter.contract.FeedAdapterContract
import com.classting.listener.OnItemClickListener
import com.classting.view.contract.MainContract

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class MainPresenter : MainContract.Presenter, OnItemClickListener {
    lateinit var presenter: MainPresenter
    lateinit var adapterModel: FeedAdapterContract.Model
    var adapterView: FeedAdapterContract.View? = null
        set(value) {
            value?.setOnItemClickListener(this)
        }


    // feed list item 클릭했을 경우
    override fun onItemClick(view: View, position: Int) {

    }
}