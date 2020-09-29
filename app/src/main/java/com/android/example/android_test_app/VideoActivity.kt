package com.android.example.android_test_app

import android.net.Uri
import android.os.Bundle
import android.os.SystemClock
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class VideoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var progressBar: ProgressBar
    private lateinit var videoViewContainer: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        val toolbar : Toolbar = findViewById(R.id.activity_video__toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        videoView = findViewById(R.id.activity_video__videoview)
        progressBar = findViewById(R.id.activity_video__progress_bar)
        videoViewContainer = findViewById(R.id.activity_video__videoview_container)
        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
        val startTime = SystemClock.elapsedRealtime()
        videoView.setMediaController(controller)
        videoView.setVideoURI(Uri.parse("android.resource://$packageName/raw/video4k"))
        videoView.setOnPreparedListener {
            videoViewContainer.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            videoView.start()
            it.isLooping = true
            findViewById<TextView>(R.id.activity_video__time_text).text = "It took "+(SystemClock.elapsedRealtime() - startTime)+" milliseconds"
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}