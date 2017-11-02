package com.dhha22.videotimeline

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.dhha22.videotimeline.adapter.FeedAdapter
import com.dhha22.videotimeline.view.contract.MainContract
import com.dhha22.videotimeline.view.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*


/**
 * Created by DavidHa on 2017. 9. 6..
 */
class MainActivity : AppCompatActivity(), MainContract.View {
    private val presenter: MainPresenter = MainPresenter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()
    }

    fun init() {
        FeedAdapter(this).let {
            recyclerView.adapter = it
            presenter.adapterModel = it
            presenter.adapterView = it
            presenter.context = this
        }
        recyclerView.addOnScrollListener(presenter)
        recyclerView.layoutManager = LinearLayoutManager(this)
        presenter.addDummyData()
    }

    override fun onResume() {
        super.onResume()
        presenter.resumeVideo()
    }

    override fun onPause() {
        super.onPause()
        presenter.pauseVideo()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        presenter.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        recyclerView.removeAllViews()
        presenter.destroyVideo()
        super.onDestroy()
    }

}