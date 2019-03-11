package com.grab.news.ui.news

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.grab.news.data.model.News
import com.grab.news.data.model.NewsDiffCallback
import com.grab.news.databinding.ItemNewsListBinding
import com.grab.news.ui.news.viewmodel.NewsListViewModel


/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListAdapter(val handler: NewsListViewModel.NewItemClickHandler) : RecyclerView.Adapter<NewsListAdapter.NewsListItemViewHolder>() {
    private var news = mutableListOf<News>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsListItemViewHolder {
        val binding = ItemNewsListBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return NewsListItemViewHolder(binding)
    }

    override fun getItemCount() = news.size

    override fun onBindViewHolder(holder: NewsListItemViewHolder, position: Int) {
        holder.onBind(news = news[position])
    }


    fun update(updatesNewsList: List<News>) {
        val diffResult = DiffUtil.calculateDiff(NewsDiffCallback(news, updatesNewsList))
        this.news.clear()
        this.news.addAll(updatesNewsList)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class NewsListItemViewHolder(var binding: ItemNewsListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(news: News) {
            binding.news = news
            binding.handler = handler
        }
    }

}