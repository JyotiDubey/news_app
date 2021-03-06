package com.grab.news.data.remote

import com.grab.news.data.model.NewsListResponse
import io.reactivex.Single

/**
 * Created by jyotidubey on 2019-03-09.
 */
interface ApiHelper{
    fun loadNews(page: Int): Single<NewsListResponse>
}