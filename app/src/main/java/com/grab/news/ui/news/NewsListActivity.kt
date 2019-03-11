package com.grab.news.ui.news

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.grab.news.R
import com.grab.news.data.model.News
import com.grab.news.ui.news.viewmodel.NewsListViewModel
import kotlinx.android.synthetic.main.activity_news_list.*

class NewsListActivity : AppCompatActivity(), NewsListViewModel.NewItemClickHandler {

    private val viewModel = NewsListViewModel()
    private var adapter: NewsListAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news_list)
        adapter = NewsListAdapter(this)
        setUpAdapter()
        viewModel.getLiveData().observe(this, Observer {
            adapter?.update(it)
        })
    }

    override fun onNewsItemClicked(news: News) {

    }

    private fun setUpAdapter() {
        new_list.layoutManager = LinearLayoutManager(this)
        new_list.adapter = adapter
    }

}
