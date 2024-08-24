package com.example.crud_web_api

import android.app.Activity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebViewDemoActivity : Activity() {

    private lateinit var webView: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_demo)
        webView = findViewById(R.id.webView)
        webView.webViewClient = WebViewClient() // Ensures links are opened within the WebView
        webView.loadUrl("http://192.168.100.11:5178/Home/ViewAll") // Replace with your URL
    }
}