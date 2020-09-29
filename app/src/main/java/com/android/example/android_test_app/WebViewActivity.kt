package com.android.example.android_test_app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actvity_webview)
        val toolbar : Toolbar = findViewById(R.id.activity_webview__toolbar)
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        webView = findViewById(R.id.activity_webview__webview)
        progressBar = findViewById(R.id.activity_webview__progress_bar)
        val startTime = SystemClock.elapsedRealtime()
        webView.settings.javaScriptEnabled = true
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
                findViewById<TextView>(R.id.activity_webview__time_text).text = "It took "+(SystemClock.elapsedRealtime() - startTime)+" milliseconds"
            }
        }
        webView.loadUrl("https://medium.com/til-kotlin/how-kotlins-delegated-properties-and-lazy-initialization-work-552cbad8be60")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}