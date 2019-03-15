package com.grab.news.data

import com.grab.news.data.model.News
import com.grab.news.data.model.NewsListResponse
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject


/**
 * Created by jyotidubey on 2019-03-09.
 */
interface DataManager{

    fun loadNewsFromServer(page: Int):Completable

    fun loadNewsFromRepository():Flowable<List<News>>

    fun insertIntoRepository(news: List<News>) : Completable

    fun invalidateAndInsertIntoRepository(news: List<News>) : Completable





}