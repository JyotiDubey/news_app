package com.grab.news.ui.news

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.ConnectivityManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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


    private val networkChangeReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val manager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val isDeviceConnectedToInternet = if (manager.activeNetworkInfo == null)
                false
            else
                manager.activeNetworkInfo.isConnected
            viewModel.updateNetworkConnectivity(isDeviceConnectedToInternet)
        }
    }

    @Inject
    internal lateinit var factory: ViewModelProvider.NewInstanceFactory
    @Inject
    internal lateinit var adapter: NewsListAdapter
    private lateinit var viewModel: NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDependencyInjections()

        viewModel = obtainViewModel()

        val binding = setUpBinding()

        binding.viewModel = viewModel

        binding.swipeToRefreshHandler = this

        binding.retryButtonClickHandler = this


        setUpAdapter()

        addLiveDataObservers()
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
        viewModel.onRetryClick()
    }

    override fun onRefresh() {

    }

    private fun performDependencyInjections() {
        NewsApplication.get(this).getAppComponent()
            .plus(NewsListModule(this)).inject(this)
    }

    private fun setUpAdapter() {
        new_list.layoutManager = LinearLayoutManager(this)
        new_list.adapter = adapter
    }

    private fun addLiveDataObservers() {
        viewModel.getNewsLiveData().observe(this, Observer {
            adapter.update(it)
        })

        viewModel.getProgressLiveData().observe(this, Observer {
            viewModel.updateProgress(it)
        })

        viewModel.getShouldShowEmptyStateLiveData().observe(this, Observer {
            viewModel.updateEmptyView(it)
        })

        viewModel.getIsNetworkConnectedStateLiveData().observe(this, Observer {
            viewModel.updateNetworkConnectivityView(it)
        })


    }

    private fun obtainViewModel() = ViewModelProviders.of(this, factory).get(NewsListViewModel::class.java)

    private fun setUpBinding() =
        DataBindingUtil.setContentView<ActivityNewsListBinding>(this, R.layout.activity_news_list)
}
