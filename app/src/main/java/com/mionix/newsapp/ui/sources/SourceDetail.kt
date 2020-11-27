package com.mionix.newsapp.ui.sources

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.content.ContextCompat
import com.mionix.newsapp.R
import kotlinx.android.synthetic.main.activity_news_detail.*
import kotlinx.android.synthetic.main.activity_news_detail.webViewDetail
import kotlinx.android.synthetic.main.activity_source_detail.*
import kotlinx.android.synthetic.main.layout_toolbar.view.*

class SourceDetail : AppCompatActivity() {
    private var url = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_source_detail)
        initView()
    }

    private fun initView() {
        initWebView()
        initToolBar()
    }
    private fun initToolBar() {
        detailSourceToolbar.ivRight.visibility = View.GONE
        detailSourceToolbar.ivLeft.background = ContextCompat.getDrawable(this@SourceDetail,R.drawable.ic_backpress)
        detailSourceToolbar.ivLeft.setOnClickListener {
            onBackPressed()
        }
    }
    @SuppressLint("SetJavaScriptEnabled")
    private fun initWebView() {
        intent.getStringExtra("urlSource")?.let {
            url =it
        }
        webViewSourceDetail.settings.javaScriptEnabled = true

        webViewSourceDetail.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                url?.let {
                    view?.loadUrl(it)
                }

                return true
            }
        }
        webViewSourceDetail.loadUrl(url)
    }
}