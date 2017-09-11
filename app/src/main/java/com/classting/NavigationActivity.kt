package com.classting


import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.v4.app.Fragment

import android.support.v7.app.AppCompatActivity
import android.view.WindowManager
import com.classting.fragment.VideoDetailFragment
import com.classting.log.Logger
import kotlinx.android.synthetic.main.fragment_video_detail.*


/**
 * Created by DavidHa on 2017. 9. 8..
 */
class NavigationActivity : AppCompatActivity() {

    companion object {
        var fragment: Fragment? = null
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Logger.v("navigation activity onCreate")

        // 비디오 상세화면 land scape 고정 및 status bar 제거
        if (fragment is VideoDetailFragment) {
            requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
            window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        }
        setContentView(R.layout.activity_navigation)
        supportFragmentManager.beginTransaction().replace(R.id.frame, fragment).commit()
        fragment = null
    }


    override fun onBackPressed() {
        val fragment = supportFragmentManager.findFragmentById(R.id.frame)

        // 비디오 상세화면에서 back 키 눌렀을 경우 비디오 정보 리스트로 전달
        if (fragment is VideoDetailFragment) {
            fragment.classtingVideoView.stop()
            val intent = Intent()
            intent.putExtra("position", fragment.position)  // 리스트 position
            intent.putExtra("positionMs", fragment.classtingVideoView.getCurrentTime()) // 비디오 재생시간
            intent.putExtra("isRecorded", fragment.classtingVideoView.isRecorded)  // 비디오 기록 여부
            setResult(Activity.RESULT_OK, intent)
            fragment.classtingVideoView.release()
        }
        super.onBackPressed()
    }
}