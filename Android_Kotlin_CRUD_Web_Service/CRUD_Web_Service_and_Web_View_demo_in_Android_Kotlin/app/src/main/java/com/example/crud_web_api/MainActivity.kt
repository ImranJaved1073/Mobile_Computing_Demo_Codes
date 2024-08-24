package com.example.crud_web_api

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button

class MainActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val buttonWebViewDemo: Button = findViewById(R.id.buttonWebViewDemo)
        val buttonSimpleWebServiceDemo: Button = findViewById(R.id.buttonSimpleWebServiceDemo)
        val buttonWebServiceListViewDemo: Button = findViewById(R.id.buttonWebServiceListViewDemo)
        val buttonPostRequestWebServiceDemo: Button = findViewById(R.id.buttonPostRequestWebServiceDemo)

        buttonWebViewDemo.setOnClickListener {
            startActivity(Intent(this, WebViewDemoActivity::class.java))
        }

        buttonSimpleWebServiceDemo.setOnClickListener {
            startActivity(Intent(this, SimpleWebServiceDemoActivity::class.java))
        }

        buttonWebServiceListViewDemo.setOnClickListener {

            startActivity(Intent(this, WebServiceListViewDemoActivity::class.java))
        }

        buttonPostRequestWebServiceDemo.setOnClickListener {
            startActivity(Intent(this, PostRequestWebServiceDemoActivity::class.java))
        }

    }
}