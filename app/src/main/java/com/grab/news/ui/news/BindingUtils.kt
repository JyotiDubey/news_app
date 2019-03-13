package com.grab.news.ui.news

import android.webkit.WebView
import androidx.databinding.BindingAdapter

/**
 * Created by jyotidubey on 2019-03-11.
 */
@BindingAdapter("webViewContent")
fun populateWebview(view: WebView, webViewContent: String) {
    val settings = view.settings
    settings.builtInZoomControls = true
    settings.displayZoomControls = false
    settings.javaScriptEnabled = true
    if (true) {
        view.loadDataWithBaseURL(null, webViewContent, "text/html; charset=utf-8", "utf-8", null)
    }
}