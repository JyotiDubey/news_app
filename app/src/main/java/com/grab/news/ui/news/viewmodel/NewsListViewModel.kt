package com.grab.news.ui.news.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.grab.news.data.NewsDataManager
import com.grab.news.data.model.News
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by jyotidubey on 2019-03-09.
 */
class NewsListViewModel {
    private val newsLiveData = MutableLiveData<List<News>>()
    private val dataManager = NewsDataManager()

    init {
        fetchNews()
    }

    private fun fetchNews() {
        dataManager.getTopHeadlines("us", 1)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                newsLiveData.value = it.news
            }, {

            })
    }

    fun getLiveData() = newsLiveData as LiveData<List<News>>

    interface NewItemClickHandler {
        fun onNewsItemClicked(news: News)
    }
}