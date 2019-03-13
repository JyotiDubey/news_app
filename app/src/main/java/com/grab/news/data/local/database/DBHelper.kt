package com.grab.news.data.local.database

import com.grab.news.data.model.News
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Observable

/**
 * Created by jyotidubey on 2019-03-12.
 */
interface DBHelper{
    fun getNews(): Flowable<List<News>>

    fun isNewsTableEmpty(): Observable<Boolean>

    fun saveLatestNews(news: List<News>): Completable

}