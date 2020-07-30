package com.android.example.android_test_app

import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class VideoActivity : AppCompatActivity() {
    private lateinit var videoView: VideoView
    private lateinit var progressBar: ProgressBar
    private lateinit var videoViewContainer: RelativeLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        videoView = findViewById(R.id.activity_video__videoview)
        progressBar = findViewById(R.id.activity_video__progress_bar)
        videoViewContainer = findViewById(R.id.activity_video__videoview_container)
        val controller = MediaController(this)
        controller.setMediaPlayer(videoView)
        videoView.setMediaController(controller)
        videoView.setVideoURI(Uri.parse("https://flutter.github.io/assets-for-api-docs/assets/videos/butterfly.mp4"))
        videoView.setOnPreparedListener {
            videoViewContainer.visibility = View.VISIBLE
            progressBar.visibility = View.GONE
            videoView.start()
        }
    }
}