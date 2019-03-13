package com.grab.news.ui.news

import android.webkit.WebView
import androidx.databinding.BindingAdapter
import com.grab.news.data.model.News

/**
 * Created by jyotidubey on 2019-03-11.
 */
class BindingUtils {
    companion object {
        @BindingAdapter("bind:webViewContent")
        @JvmStatic
        fun populateWebview(view: WebView, webViewContent: News) {
            val settings = view.settings
            settings.builtInZoomControls = true
            settings.displayZoomControls = false
            settings.javaScriptEnabled = true
            view.loadUrl(webViewContent.url)

        }
    }
}