package com.example.withpet.ui.common

import android.os.Bundle
import com.example.withpet.R
import com.example.withpet.core.BaseActivity
import com.example.withpet.ui.common.view.BaseWebView

class WebViewActivity : BaseActivity() {

    private val url: String? by lazy { intent.getStringExtra(URL) }
    private val webview: BaseWebView? by lazy { findViewById<BaseWebView?>(R.id.web) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.webview_activity)
        onLoadOnce()
    }

    private fun onLoadOnce() {
        showProgress()
        url?.let { webview?.loadUrl(url) } ?: dismissProgress()
    }

    companion object {
        const val URL = "URL"
    }
}