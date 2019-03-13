package com.grab.news.data.local.database


import com.grab.news.data.model.News
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by jyotidubey on 2019-03-12.
 */
class NewsDBHelper @Inject constructor(private val newsDao :NewsDao) : DBHelper {

    override fun getNews() = newsDao.loadAll()

    override fun updateNews(news: List<News>) = Completable.create{
        newsDao.cleanAndInsert(news)
        it.onComplete()
    }

}