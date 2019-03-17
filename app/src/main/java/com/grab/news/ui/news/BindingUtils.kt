package com.grab.news.ui.news

import android.webkit.WebView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.grab.news.R
import com.grab.news.data.imageloader.ImageLoader
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
            settings.apply {
                builtInZoomControls = true
                displayZoomControls = false
                javaScriptEnabled = true
            }
            webViewContent.url?.let { view.loadUrl(it) }

        }

        @BindingAdapter(value= ["imageLoader", "src"], requireAll = false)
        @JvmStatic
        fun populateImage(view: ImageView, imageLoader: ImageLoader, url: String?) {
            url?.let {
                imageLoader.loadImage(view,url, ContextCompat.getDrawable(view.context,R.drawable.ic_newspaper))
            }
//            Glide.with(view.context)
//                .load(url)
//                .apply(RequestOptions().error(R.drawable.ic_newspaper))
//                .into(view)

        }

        @BindingAdapter("bind:onRefresh")
        @JvmStatic
        fun onSwipeToRefresh(view: SwipeRefreshLayout ,handler: SwipeRefreshLayout.OnRefreshListener) {
            view.setOnRefreshListener(handler)
        }
    }
}