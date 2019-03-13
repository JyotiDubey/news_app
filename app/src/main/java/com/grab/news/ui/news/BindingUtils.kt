package com.grab.news.ui.news

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
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

        @BindingAdapter("bind:src")
        @JvmStatic
        fun populateImage(view: ImageView ,url: String) {
            Glide.with(view.context)
                .load(url)
                .into(view)

        }
    }
}