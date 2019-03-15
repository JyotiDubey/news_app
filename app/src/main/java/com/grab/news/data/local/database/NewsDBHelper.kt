package com.grab.news.data.local.database


import com.grab.news.data.model.News
import io.reactivex.Completable
import javax.inject.Inject


/**
 * Created by jyotidubey on 2019-03-12.
 */
class NewsDBHelper @Inject constructor(private val newsDao: NewsDao) : DBHelper {

    override fun getNewsFromRepository() = newsDao.loadAll()

    override fun insertIntoRepository(news: List<News>) = Completable.create {
        newsDao.insertAll(news)
        it.onComplete()
    }

    override fun invalidateAndInsertIntoRepository(news: List<News>) = Completable.create {
        newsDao.clearAndInsert(news)
        it.onComplete()
    }

}