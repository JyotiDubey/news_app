package com.grab.news.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.grab.news.NewsApplication
import com.grab.news.R
import com.grab.news.data.model.News
import com.grab.news.databinding.ActivityNewsListBinding
import com.grab.news.di.module.NewsListModule
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*
import javax.inject.Inject



class NewsListActivity : AppCompatActivity(), NewsListViewModel.NewItemClickHandler {

    @Inject
    internal lateinit var factory: ViewModelProvider.NewInstanceFactory
    @Inject
    internal lateinit var adapter: NewsListAdapter
    private lateinit var viewModel : NewsListViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        performDependencyInjections()

        viewModel = obtainViewModel()

        val binding = setUpBinding()

        binding.viewModel = viewModel

        setUpAdapter()

        addLiveDataObservers()
    }


    override fun onNewsItemClicked(news: News) {
        NewsDetailActivity.startNewsDetailActivity(this,news)
    }

    private fun performDependencyInjections() {
        NewsApplication.get(this).getAppComponent()
            .plus(NewsListModule(this)).inject(this)
    }

    private fun setUpAdapter() {
        new_list.layoutManager = LinearLayoutManager(this)
        new_list.adapter = adapter
        new_list.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                if (!new_list.canScrollVertically(1)) {
                    viewModel.loadNextPage()
                }
            }
        })

    }

    private fun addLiveDataObservers() {
        viewModel.getNewsLiveData().observe(this, Observer {
            adapter.update(it)
        })

        viewModel.getProgressLiveData().observe(this, Observer {
            viewModel.updateProgress(it)
        })
    }

    private fun obtainViewModel() = ViewModelProviders.of(this,factory).get(NewsListViewModel::class.java)

    private fun setUpBinding() = DataBindingUtil.setContentView<ActivityNewsListBinding>(this, R.layout.activity_news_list)
}
