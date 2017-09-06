package com.classting

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import com.classting.adapter.FeedAdapter
import com.classting.view.contract.MainContract
import com.classting.view.presenter.MainPresenter
import kotlinx.android.synthetic.main.activity_main.*

/**
 * Created by DavidHa on 2017. 9. 6..
 */
class MainActivity : AppCompatActivity() , MainContract.View{
    private val presenter : MainPresenter = MainPresenter()


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
        }
        recyclerView.layoutManager = LinearLayoutManager(this)
    }
}