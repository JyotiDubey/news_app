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

    fun getNews(country: String, page: Int):Flowable<List<News>>

    fun loadNewsFromApi(country: String, page: Int):Single<NewsListResponse>

    fun publishSubject():PublishSubject<Boolean>



}