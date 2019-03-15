package com.grab.news.ui.news

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.grab.news.NewsApplication
import com.grab.news.R
import com.grab.news.data.model.News
import com.grab.news.databinding.ActivityNewsDetailBinding
import com.grab.news.ui.news.viewmodel.NewsDetailViewModel
import kotlinx.android.synthetic.main.activity_news_detail.*
import javax.inject.Inject


/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsDetailActivity : AppCompatActivity() {


    @Inject
    internal lateinit var factory: ViewModelProvider.NewInstanceFactory

    private lateinit var viewModel: NewsDetailViewModel

    companion object {

        private const val EXTRA_NEWS = "Intent:Extra:News"

        fun startNewsDetailActivity(context: Context, news: News) {
            var intent = Intent(context, NewsDetailActivity::class.java)
            intent.putExtra(EXTRA_NEWS, news)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        performDependencyInjections()

        viewModel = obtainViewModel()


        val binding = setUpBinding()

        binding.news = intent.extras.getParcelable(EXTRA_NEWS) as News

        binding.lifecycleOwner = this

        binding.viewModel = viewModel

        setupWebView()

    }

    private fun setupWebView() {
        webView.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView, url: String) {
                super.onPageFinished(webView, url)
                viewModel.hideProgress()

            }

            override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
                super.onPageStarted(view, url, favicon)
                viewModel.showProgress()

            }

        }

    }

    private fun setUpBinding() =
        DataBindingUtil.setContentView<ActivityNewsDetailBinding>(this, R.layout.activity_news_detail)

    private fun obtainViewModel() = ViewModelProviders.of(this, factory).get(NewsDetailViewModel::class.java)

    private fun performDependencyInjections() {
        NewsApplication.get(this).getAppComponent().inject(this)
    }

}
