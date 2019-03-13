package com.grab.news.ui.news

import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.grab.news.R
import com.grab.news.data.model.News
import com.grab.news.databinding.ActivityNewsDetailBinding
import kotlinx.android.synthetic.main.activity_news_detail.*

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDetailActivity : AppCompatActivity(){

    companion object {
        fun startNewsDetailActivity(context: Context, news: News){
            var intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra("News",news)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivityNewsDetailBinding>(this, R.layout.activity_news_detail)
        var news  = intent.extras.getParcelable("News") as News
        val settings = webView.settings
        settings.builtInZoomControls = true
        settings.displayZoomControls = false
        settings.javaScriptEnabled = true
        if (true) {
            webView.loadUrl( news.url)
        }



    }
}
