package com.grab.news.ui.news

import android.webkit.WebView
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.grab.news.R
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
            webViewContent.url?.let { view.loadUrl(it) }

        }

        @BindingAdapter("bind:src")
        @JvmStatic
        fun populateImage(view: ImageView ,url: String?) {
            Glide.with(view.context)
                .load(url)
                .apply(RequestOptions().error(R.drawable.ic_newspaper))
                .into(view)

        }

        @BindingAdapter("bind:onRefresh")
        @JvmStatic
        fun onSwipeToRefresh(view: SwipeRefreshLayout ,handler: SwipeRefreshLayout.OnRefreshListener) {
            view.setOnRefreshListener(handler)
        }
    }
}