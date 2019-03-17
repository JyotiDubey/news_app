package com.grab.news.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.grab.news.data.model.News
import com.grab.news.data.model.NewsDiff
import com.grab.news.databinding.ItemNewsListBinding
import com.grab.news.ui.news.viewmodel.NewsListViewModel


/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListAdapter(val handler: NewsListViewModel.NewsListScreenActionHandler) :
    ListAdapter<News, NewsListAdapter.NewsListItemViewHolder>(NewsDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListItemViewHolder {
        val binding = ItemNewsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewsListItemViewHolder(binding, handler)
    }

    override fun onBindViewHolder(holder: NewsListItemViewHolder, position: Int) {
        holder.onBind(news = getItem(position))
    }

    class NewsListItemViewHolder(
        private var binding: ItemNewsListBinding,
        private var handler: NewsListViewModel.NewsListScreenActionHandler
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(news: News) {
            binding.news = news
            binding.handler = handler
        }
    }
}