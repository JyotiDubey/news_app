package com.grab.news.data.local.database

import com.grab.news.data.model.News
import io.reactivex.Completable
import io.reactivex.Flowable

/**
 * Created by jyotidubey on 2019-03-12.
 */
interface DBHelper{
    fun getNewsFromRepository(): Flowable<List<News>>

    fun insertIntoRepository(news: List<News>): Completable

    fun invalidateAndInsertIntoRepository(news: List<News>) : Completable
}