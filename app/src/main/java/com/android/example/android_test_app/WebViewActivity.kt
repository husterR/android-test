package com.android.example.android_test_app

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
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
        webView.webViewClient = object : WebViewClient(){
            override fun onPageFinished(view: WebView?, url: String?) {
                progressBar.visibility = View.GONE
                webView.visibility = View.VISIBLE
            }
        }
        webView.settings.javaScriptEnabled = true
        webView.loadUrl("https://stackoverflow.com/questions/57584072/simpletarget-is-deprecated-glide")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}