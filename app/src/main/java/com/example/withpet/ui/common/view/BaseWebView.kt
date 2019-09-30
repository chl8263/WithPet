package com.example.withpet.ui.common.view

import android.content.Context
import android.graphics.Bitmap
import android.util.AttributeSet
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.example.withpet.core.BaseActivity

class BaseWebView : WebView {
    val mContext : Context = context

    constructor(context: Context?) : super(context)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, defStyleRes: Int) : super(context, attrs, defStyleAttr, defStyleRes)
    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int, privateBrowsing: Boolean) : super(context, attrs, defStyleAttr, privateBrowsing)

    init {
        settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
        settings.builtInZoomControls = true
        webViewClient = BWebViewClient()
    }

    inner class BWebViewClient : WebViewClient() {
        override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
            super.onPageStarted(view, url, favicon)
            if(mContext is BaseActivity){
                mContext.showProgress()
            }
        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            if(mContext is BaseActivity){
                mContext.dismissProgress()
            }
        }
    }

}

