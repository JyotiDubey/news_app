package com.grab.news.ui.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.grab.news.NewsApplication
import com.grab.news.R
import com.grab.news.data.model.News
import com.grab.news.databinding.ActivityNewsListBinding
import com.grab.news.di.module.NewsListModule
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*
import javax.inject.Inject


class NewsListActivity : AppCompatActivity(), NewsListViewModel.NewsListScreenActionHandler,
    SwipeRefreshLayout.OnRefreshListener {

    @Inject
    internal lateinit var factory: ViewModelProvider.NewInstanceFactory
    @Inject
    internal lateinit var adapter: NewsListAdapter
    @Inject
    internal lateinit var layoutManager: LinearLayoutManager

    private lateinit var viewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDependencyInjections()

        obtainViewModel()

        setUpBinding()

        setUpAdapter()

        addNewsListDataUpdateObserver()
    }

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION)
        registerReceiver(networkChangeReceiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(networkChangeReceiver)
    }


    override fun onNewsItemClicked(news: News) {
        NewsDetailActivity.startNewsDetailActivity(this, news)
    }

    override fun onRetryButtonClicked() {
        viewModel.onRequestRetry()
    }

    override fun onRefresh() {
        viewModel.onRequestRefresh()
    }

    private fun performDependencyInjections() {
        NewsApplication.get(this).getAppComponent()
            .plus(NewsListModule(this, this)).inject(this)
    }

    private fun setUpAdapter() {
        newList.layoutManager = layoutManager
        newList.adapter = adapter
        (newList.itemAnimator as? SimpleItemAnimator)?.supportsChangeAnimations = false
        newList.addOnScrollListener(object : RecyclerView.OnScrollListener() {

            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!newList.canScrollVertically(1)) {
                    viewModel.onRequestLoadMore()
                }
            }
        })
    }

    private fun addNewsListDataUpdateObserver() {
        viewModel.newsLiveData().observe(this, Observer { news ->
            adapter.submitList(news)
        })
    }

    private fun obtainViewModel() {
        viewModel = ViewModelProviders.of(this, factory).get(NewsListViewModel::class.java)
    }

    private fun setUpBinding(){
        val binding = DataBindingUtil.setContentView<ActivityNewsListBinding>(this, R.layout.activity_news_list)
        binding.lifecycleOwner = this
        binding.swipeToRefreshHandler = this
        binding.retryButtonClickHandler = this
        binding.viewModel = viewModel
    }


    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isDeviceOfflineState = if (manager.activeNetworkInfo == null)
                true
            else
                !manager.activeNetworkInfo.isConnected
            viewModel.onNetworkConnectivityChanged(isDeviceOfflineState)
        }
    }
}
