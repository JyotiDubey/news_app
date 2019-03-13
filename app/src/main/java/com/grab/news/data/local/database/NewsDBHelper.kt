package com.grab.news.data.local.database


import com.grab.news.data.model.News
import io.reactivex.Completable
import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by jyotidubey on 2019-03-12.
 */
class NewsDBHelper @Inject constructor(private val database:AppDatabase) : DBHelper {

    override fun getNews() = database.newsDao().loadAll()

    override fun isNewsTableEmpty(): Observable<Boolean> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun saveLatestNews(news: List<News>) = Completable.create{
        database?.newsDao()?.cleanAndInsert(news)
        it.onComplete()
    }

}