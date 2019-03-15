package com.grab.news.data

import com.grab.news.data.model.News
import com.grab.news.data.model.NewsListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single


/**
 * Created by jyotidubey on 2019-03-09.
 */

interface DataManager {

    fun loadNewsFromServer(page: Int): Single<NewsListResponse>

    fun loadNewsFromRepository(): Flowable<List<News>>

    fun updateRepository(page: Int, news:List<News>): Completable
}