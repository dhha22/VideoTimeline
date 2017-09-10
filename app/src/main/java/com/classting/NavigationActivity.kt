package com.classting


import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity
import com.classting.fragment.VideoDetailFragment
import com.classting.log.Logger
import kotlinx.android.synthetic.main.fragment_video_detail.*


/**
 * Created by DavidHa on 2017. 9. 8..
 */
class NavigationActivity : AppCompatActivity() {

   companion object {
       lateinit var fragment : Fragment
   }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v("navigation activity onCreate")
        if(fragment is VideoDetailFragment) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        }
        setContentView(R.layout.activity_navigation)
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)
        if (fragment is VideoDetailFragment) {  // Video 상세화면에서 Back 키 눌렀을 경우 Video Position 을 가져온다
            val intent = Intent()
            intent.putExtra("position", fragment.position)
            intent.putExtra("positionMs", fragment.classtingVideoView.getCurrentPosition())
            setResult(Activity.RESULT_OK, intent)
        }
        super.onBackPressed()
    }
}